package com.idata365.app.controller.securityV2;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.IndexSlipPage;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.CompetitorFamilyInfoResultBean;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.FamilyDriveDayStat;
import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.DicService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.service.common.FamilyScoreService;
import com.idata365.app.serviceV2.GameServiceV2;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class GameControllerV2 extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(GameControllerV2.class);

	@Autowired
	private GameServiceV2 gameService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private DicService dicService;
	@Autowired
	private FamilyScoreService familyScoreService;
	@Autowired
	private FamilyService familyService;

	/**
	 * 
	 * @Title: getIndexFightInfo
	 * @Description: TODO(首页对战信息显示)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */

	@RequestMapping("/getIndexFightInfo")
	public Map<String, Object> getIndexFightInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setUserId(userId);
		ScoreFamilyInfoAllBean queryFamily = scoreService.queryFamily(bean);

		if (ValidTools.isBlank(queryFamily)) {
			return ResultUtils.rtSuccess(null);
		}
		List<Map<String, String>> infoList = new ArrayList<>();
		if (ValidTools.isNotBlank(queryFamily.getOriFamily())) {
			extractedList(queryFamily.getOriFamily(), infoList);
		}
		if (ValidTools.isNotBlank(queryFamily.getJoinFamily())) {
			extractedList(queryFamily.getJoinFamily(), infoList);
		}

		return ResultUtils.rtSuccess(infoList);
	}

	private void extractedList(ScoreFamilyInfoBean scoreFamilyInfoBean, List<Map<String, String>> infoList) {
		if (ValidTools.isNotBlank(scoreFamilyInfoBean)) {
			Map<String, String> map = new HashMap<>();
			String familyId = String.valueOf(scoreFamilyInfoBean.getFamilyId());
			map.put("familyId", familyId);
			map.put("familyName", scoreFamilyInfoBean.getName());
			double sc = familyScoreService.familyScore(Long.valueOf(familyId), getCurrentDayStr());
			BigDecimal b = new BigDecimal(sc);
			map.put("familyScore", b.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
			String haveNewPower = gameService.queryHaveNewPower(this.getUserId(),familyId);
			map.put("haveNewPower", haveNewPower);
			String fightingTime = null;
			CompetitorFamilyInfoResultBean resultBean = this.gameService
					.queryCompetitorFamilyInfo(Long.valueOf(familyId), fightingTime);
			if (null != resultBean) {
				long fightFamilyId = Long.valueOf(resultBean.getCompetitorFamilyId());
				Map<String, Object> fightFamily = familyService.findFamilyByFamilyId(fightFamilyId);
				map.put("fightFamilyId", String.valueOf(fightFamilyId));
				map.put("fightFamilyName", fightFamily.get("familyName").toString());
				map.put("fightFamilyScore", BigDecimal
						.valueOf(familyScoreService.familyScore(Long.valueOf(fightFamilyId), getCurrentDayStr()))
						.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
				infoList.add(map);
			}else {
				map.put("fightFamilyName", "好车族教官");
				infoList.add(map);
			}

		}
	}

	/**
	 * 
	 * @Title: billBoard
	 * @Description: TODO(实时榜单)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/billBoard")
	public Map<String, Object> billBoard(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		String billBoardType = requestBodyParams.get("billBoardType").toString();
		String sign = SignUtils.encryptHMAC(String.valueOf(billBoardType));

		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setUserId(userId);
		ScoreFamilyInfoAllBean queryFamily = scoreService.queryFamily(bean);

		List<Map<String, String>> billList = new ArrayList<>();
		if ("1".equals(billBoardType)) {
			billList = gameService.billBoard(queryFamily);
		} else if ("2".equals(billBoardType) || "3".equals(billBoardType)) {
			billList = chezuAssetService.billBoard(billBoardType, userId, sign);
			if (ValidTools.isNotBlank(billList)) {
				for (int i = 0; i < billList.size(); i++) {
					long id = Long.valueOf(billList.get(i).get("userId"));
					UsersAccount account = userInfoService.getUsersAccount(id);
					billList.get(i).put("name", account.getNickName() == null ? PhoneUtils.hidePhone(account.getPhone())
							: account.getNickName());
				}
			}
		} else {
			return ResultUtils.rtSuccess(null);
		}
		// 规则
		// Map<String, Object> result = new HashMap<>();
		// result.put("billList", billList);
		// result.put("rule", RuleConstant.Rule);
		return ResultUtils.rtSuccess(billList);
	}

	/**
	 * 
	 * @Title: fightingDetail
	 * @Description: TODO(对战详情)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/fightingDetail")
	public Map<String, Object> fightingDetail(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		long myFamilyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		String fightingTime = null;
		CompetitorFamilyInfoResultBean resultBean = this.gameService.queryCompetitorFamilyInfo(myFamilyId,
				fightingTime);
		if (null == resultBean) {
			return ResultUtils.rtSuccess(null);
		}
		long fightFamilyId = Long.valueOf(resultBean.getCompetitorFamilyId());
		long[] arr = { myFamilyId, fightFamilyId };
		List<Map<String, Object>> data = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		String daystamp = null;
		for (int i = 0; i < arr.length; i++) {
			Map<String, String> infoFamily = gameService.getInfoByFamilyId(arr[i], daystamp);
			Map<String, Object> familyInfo = new HashMap<>();
			familyInfo.put("familyName", infoFamily.get("name"));
			double sc = familyScoreService.familyScore(Long.valueOf(arr[i]), getCurrentDayStr());
			BigDecimal b = new BigDecimal(sc);
			familyInfo.put("familyScore", b.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
			familyInfo.put("trophyNum", infoFamily.get("trophyNum"));
			familyInfo.put("grade", infoFamily.get("gradeOrNum"));

			ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
			bean.setFamilyId(arr[i]);
			List<ScoreMemberInfoResultBean> member = scoreService.listFamilyMember(bean);
			List<Map<String, String>> memberScoreS = new ArrayList<>();
			for (int j = 0; j < member.size(); j++) {
				Map<String, String> memberScore = new HashMap<>();
				String memberId = member.get(j).getUserId();
				double score = scoreService.getAvgScore(memberId, arr[i]);
				UsersAccount account = userInfoService.getUsersAccount(Long.valueOf(memberId));
				memberScore.put("name", account.getNickName() == null ? PhoneUtils.hidePhone(account.getPhone())
						: account.getNickName());
				memberScore.put("score", String.valueOf(score));
				memberScoreS.add(memberScore);
			}
			Collections.sort(memberScoreS, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					return Double.valueOf(o2.get("score")).compareTo(Double.valueOf(o1.get("score")));
				}
			});

			familyInfo.put("memberScoreS", memberScoreS);

			data.add(familyInfo);

			if (i == 0) {
				// 我的家族等级
				String familyTypeValue = infoFamily.get("gradeOrNum");
				String showInfo = "赛季结束后钻石段位玩家可获得：1200钻石";
				if (familyTypeValue.contains("钻石") || familyTypeValue.contains("冠军")) {
					showInfo = "当前段位赛季结束后可获得：1200钻石";
				}
				String win = null;
				String loss = null;
				List<DicFamilyType> types = dicService.getDicFamilyType();
				for (DicFamilyType type : types) {
					if (type.getFamilyTypeValue().equals(familyTypeValue)) {
						win = "奖杯+" + type.getWin() + " 大量钻石！";
						if (type.getLoss() >= 0) {
							loss = "奖杯+" + type.getLoss();
						} else {
							loss = "奖杯" + type.getLoss();
						}

					}
				}
				result.put("reward", win);
				result.put("punishment", loss);
				try {
					result.put("surPlusDays", dicService.getSurPlusDays());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.put("showInfo", showInfo);
			}
		}
		result.put("myFamilyInfo", data.get(0));
		result.put("fightFamilyInfo", data.get(1));

		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 
	 * @Title: fightingRecord
	 * @Description: TODO(对战记录)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/fightingRecord")
	public Map<String, Object> fightingRecord(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		long recordId = Long.valueOf(requestBodyParams.get("recordId").toString());
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setFamilyId(familyId);
		ScoreFamilyDetailResultBean familyDetail = scoreService.queryFamilyDetail(bean);
		Map<String, Object> map = new HashMap<>();
		String daystam = null;
		Map<String, String> infoFamily = gameService.getInfoByFamilyId(familyId, daystam);
		map.put("familyName", familyDetail.getFamilyName());
		map.put("rank", familyDetail.getOrderNo());
		map.put("trophyNum", infoFamily.get("trophyNum"));
		map.put("familyImg", super.getImgBasePath() + familyDetail.getImgUrl());
		map.put("grade", infoFamily.get("gradeOrNum"));
		long fightFamilyId;
		List<FamilyRelation> recordList = gameService.queryFightRecordByFamilyId(Long.valueOf(familyId), recordId);
		List<Map<String, String>> result = new ArrayList<>();
		if (recordList == null || recordList.size() == 0) {
			map.put("result", result);
			return ResultUtils.rtSuccess(map);
		}
		for (int i = 0; i < recordList.size(); i++) {
			Map<String, String> data = new HashMap<>();
			String daystamp = recordList.get(i).getDaystamp();
			if (familyId == recordList.get(i).getSelfFamilyId()) {
				fightFamilyId = recordList.get(i).getCompetitorFamilyId();
			} else {
				fightFamilyId = recordList.get(i).getSelfFamilyId();
			}
			bean.setFamilyId(fightFamilyId);
			ScoreFamilyDetailResultBean detail = scoreService.queryFamilyDetail(bean);
			String fightFamilyImg = detail.getImgUrl();
			String fightFamilyName = detail.getFamilyName();
			String fightFamilyScore = "0";
			String myFamilyScore = "0";
			FamilyDriveDayStat familyDriveDayStat = gameService.queryFamilyScore(fightFamilyId, daystamp);
			if (ValidTools.isNotBlank(familyDriveDayStat)) {
				fightFamilyScore = familyDriveDayStat.getScore().toString();
			}
			FamilyDriveDayStat familyDriveDayStat2 = gameService.queryFamilyScore(familyId, daystamp);
			if (ValidTools.isNotBlank(familyDriveDayStat2)) {
				myFamilyScore = familyDriveDayStat2.getScore().toString();
			}
			String status;
			if (Double.valueOf(myFamilyScore) > Double.valueOf(fightFamilyScore)) {
				status = "1";
			} else if (Double.valueOf(myFamilyScore) < Double.valueOf(fightFamilyScore)) {
				status = "2";
			} else {
				status = "0";
			}
			data.put("recordId", recordList.get(i).getId().toString());
			data.put("fightingTime", daystamp);
			data.put("status", status);
			data.put("myFamilyScore", myFamilyScore);
			data.put("fightFamilyId", fightFamilyScore);
			data.put("fightFamilyScore", fightFamilyScore);
			data.put("fightFamilyName", fightFamilyName);
			data.put("fightFamilyImg", super.getImgBasePath() + fightFamilyImg);
			result.add(data);
		}
		map.put("result", result);
		return ResultUtils.rtSuccess(map);
	}

	/**
	 * 
	 * @Title: fightingHistoryScore
	 * @Description: TODO(历史成绩)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/fightingHistoryScore")
	public Map<String, Object> fightingHistoryScore(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		long myFamilyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		String daystamp = requestBodyParams.get("fightingTime").toString();
		String sign = SignUtils.encryptHMAC(daystamp);
		CompetitorFamilyInfoResultBean resultBean = this.gameService.queryCompetitorFamilyInfo(myFamilyId, daystamp);
		if (null == resultBean) {
			return ResultUtils.rtSuccess(null);
		}
		long fightFamilyId = Long.valueOf(resultBean.getCompetitorFamilyId());
		String win = "";
		String loss = "";

		long[] arr = { myFamilyId, fightFamilyId };
		List<Map<String, Object>> data = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			Map<String, String> infoFamily = gameService.getInfoByFamilyId(arr[i], daystamp);
			Map<String, Object> familyInfo = new HashMap<>();
			familyInfo.put("familyName", infoFamily.get("name"));
			FamilyDriveDayStat familyDriveDayStat = gameService.queryFamilyScore(arr[i], daystamp);
			String familyScore = familyDriveDayStat.getScore().toString();
			familyInfo.put("familyScore", familyScore);
			familyInfo.put("trophyNum", infoFamily.get("trophyNum"));
			familyInfo.put("grade", infoFamily.get("gradeOrNum"));

			ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
			bean.setFamilyId(arr[i]);

			List<Map<String, Object>> user = scoreService.getMemberInfoByTime(arr[i], daystamp);
			List<Map<String, String>> memberScoreS = new ArrayList<>();
			for (int j = 0; j < user.size(); j++) {
				Map<String, String> memberScore = new HashMap<>();
				String memberId = user.get(j).get("userId").toString();
				String score = user.get(j).get("avgScore").toString();
				UsersAccount account = userInfoService.getUsersAccount(Long.valueOf(memberId));
				memberScore.put("name", account.getNickName() == null ? PhoneUtils.hidePhone(account.getPhone())
						: account.getNickName());
				memberScore.put("score", score);
				memberScoreS.add(memberScore);
			}
			Collections.sort(memberScoreS, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					return Double.valueOf(o2.get("score")).compareTo(Double.valueOf(o1.get("score")));
				}
			});
			familyInfo.put("memberScoreS", memberScoreS);

			data.add(familyInfo);

			if (i == 0) {
				// 我的家族等级
				String familyTypeValue = infoFamily.get("gradeOrNum");

				List<DicFamilyType> types = dicService.getDicFamilyType();
				for (DicFamilyType type : types) {
					if (type.getFamilyTypeValue().equals(familyTypeValue)) {
						win = "奖杯+" + type.getWin() + "";
						if (type.getLoss() >= 0) {
							loss = "奖杯+" + type.getLoss();
						} else {
							loss = "奖杯" + type.getLoss();
						}
					}
				}
			}
		}
		result.put("myFamilyInfo", data.get(0));
		result.put("fightFamilyInfo", data.get(1));
		if (Double.valueOf(data.get(0).get("familyScore").toString()) > Double
				.valueOf(data.get(1).get("familyScore").toString())) {
			result.put("rewardAndPunishment", win);
			result.put("status", "1");
		} else if (Double.valueOf(data.get(0).get("familyScore").toString()) < Double
				.valueOf(data.get(1).get("familyScore").toString())) {
			result.put("rewardAndPunishment", loss);
			result.put("status", "2");
		} else {
			result.put("rewardAndPunishment", "和气生财！！！");
			result.put("status", "0");
		}
		result.put("familySeasonID", String.valueOf(chezuAssetService.getFamilySeasonID(daystamp, myFamilyId, sign)));
		return ResultUtils.rtSuccess(result);
	}

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}

	public String getYesterdayDateStr() {
		Date curDate = Calendar.getInstance().getTime();
		Date yesterdayDate = DateUtils.addDays(curDate, -1);

		String yesterdayDateStr = DateFormatUtils.format(yesterdayDate, "yyyy-MM-dd");
		LOG.info(yesterdayDateStr);
		return yesterdayDateStr;
	}
	
	/**
	 * 
	 * @Title: getSlipPage
	 * @Description: TODO(滑动引导页)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getSlipPage")
	public Map<String, Object> getSlip(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> map = new HashMap<>();
		map.put("isNew", "1");
		map.put("fireDate", "1970-01-01");
		String [] pages = {IndexSlipPage.ONE,IndexSlipPage.TWO,IndexSlipPage.THREE};
		map.put("pages", pages);
		return ResultUtils.rtSuccess(map);
	}
	
}
