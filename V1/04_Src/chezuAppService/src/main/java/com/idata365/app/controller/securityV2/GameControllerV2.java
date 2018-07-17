package com.idata365.app.controller.securityV2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.idata365.app.entity.FamilyDriveDayStat;
import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.FightService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.TaskService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.service.common.FamilyScoreService;
import com.idata365.app.serviceV2.GameServiceV2;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class GameControllerV2 extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(GameControllerV2.class);

	@Autowired
	private GameServiceV2 gameServiceV2;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private FamilyScoreService familyScoreService;
	@Autowired
	private FamilyService familyService;
	@Autowired
	TaskService taskService;
	@Autowired
	FightService fightService;

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
		// 更新活跃时间
		taskService.updateLastLoginTimeByUserId(userId);
		LOG.info("userId=================" + userId);
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setUserId(userId);
		ScoreFamilyInfoAllBean queryFamily = scoreService.queryFamily(bean);
		if (ValidTools.isBlank(queryFamily)) {
			return ResultUtils.rtSuccess(null);
		}
		List<Map<String, String>> rtList = new ArrayList<>();
		extractedList(queryFamily.getOriFamily(), rtList);
		extractedList(queryFamily.getJoinFamily(), rtList);

		return ResultUtils.rtSuccess(rtList);
	}

	private void extractedList(ScoreFamilyInfoBean bean, List<Map<String, String>> infoList) {
		if (ValidTools.isBlank(bean) || bean.getFamilyId() <= 0) {
			return;
		}
		Map<String, String> map = new HashMap<>();
		Long familyId = Long.valueOf(String.valueOf(bean.getFamilyId()));

		// 明日对战家族ID
		Long flag = fightService.getOpponentIdBySelfId(familyId, DateTools.getTomorrowDateStr());
		if (flag == null || flag == 0) {
			map.put("isChallengeFamilyToday", "0");
		} else {
			map.put("isChallengeFamilyToday", "1");
		}

		// 当前对战家族ID
		Long opponentId = fightService.getOpponentIdBySelfId(familyId, DateTools.getYYYY_MM_DD());
		map.put("familyId", String.valueOf(familyId));
		map.put("familyName", bean.getName());
		double sc = familyScoreService.familyScore(familyId, getCurrentDayStr());
		BigDecimal b = new BigDecimal(sc);
		map.put("familyScore", b.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		long userId = this.getUserId();
		String haveNewPower = chezuAssetService.queryHaveNewPower(userId, bean.getFamilyId(),
				SignUtils.encryptHMAC(String.valueOf(userId)));
		map.put("haveNewPower", haveNewPower == null ? "0" : haveNewPower);
		if (opponentId == null || opponentId == 0) {
			infoList.add(map);
			return;
		}

		Map<String, Object> fightFamily = familyService.findFamilyByFamilyId(opponentId);
		map.put("fightFamilyId", String.valueOf(opponentId));
		map.put("fightFamilyName", fightFamily.get("familyName").toString());
		map.put("fightFamilyScore", BigDecimal.valueOf(familyScoreService.familyScore(opponentId, getCurrentDayStr()))
				.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
		infoList.add(map);
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
			billList = gameServiceV2.billBoard(queryFamily);
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

		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> data = new ArrayList<>();
		long userId = this.getUserId();
		long myFamilyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("userId=================" + userId);
		LOG.info("myFamilyId=================" + myFamilyId);
		Long opponentId = fightService.getOpponentIdBySelfId(myFamilyId, DateTools.getYYYY_MM_DD());
		if (opponentId == null || opponentId == 0) {
			return null;
		}
		result = this.gameServiceV2.getFightingDetail(result, data, myFamilyId, opponentId);
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
		Map<String, Object> rtMap = new HashMap<>();
		List<Map<String, String>> result = new ArrayList<>();
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		long recordId = Long.valueOf(requestBodyParams.get("recordId").toString());
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setFamilyId(familyId);
		ScoreFamilyDetailResultBean familyDetail = scoreService.queryFamilyDetail(bean);
		String daystam = null;
		Map<String, String> infoFamily = gameServiceV2.getInfoByFamilyId(familyId, daystam);
		rtMap.put("familyName", familyDetail.getFamilyName());
		rtMap.put("rank", familyDetail.getOrderNo());
		rtMap.put("trophyNum", infoFamily.get("trophyNum"));
		rtMap.put("familyImg", super.getImgBasePath() + familyDetail.getImgUrl());
		rtMap.put("grade", infoFamily.get("gradeOrNum"));
		long fightFamilyId;
		List<FamilyRelation> recordList = gameServiceV2.queryFightRecordByFamilyId(Long.valueOf(familyId), recordId);
		if (recordList == null || recordList.size() == 0) {
			rtMap.put("result", result);
			return ResultUtils.rtSuccess(rtMap);
		}
		for (int i = 0; i < recordList.size(); i++) {
			Map<String, String> data = new HashMap<>();
			String daystamp = recordList.get(i).getDaystamp();
			if (familyId == recordList.get(i).getSelfFamilyId()) {
				fightFamilyId = recordList.get(i).getCompetitorFamilyId();
			} else {
				fightFamilyId = recordList.get(i).getSelfFamilyId();
			}
			if (fightFamilyId == 0) {
				continue;
			}
			bean.setFamilyId(fightFamilyId);
			ScoreFamilyDetailResultBean detail = scoreService.queryFamilyDetail(bean);
			String fightFamilyImg = detail.getImgUrl();
			String fightFamilyName = detail.getFamilyName();
			String fightFamilyScore = "0";
			String myFamilyScore = "0";
			FamilyDriveDayStat familyDriveDayStat = gameServiceV2.queryFamilyScore(fightFamilyId, daystamp);
			fightFamilyScore = familyDriveDayStat.getScore().toString();
			FamilyDriveDayStat familyDriveDayStat2 = gameServiceV2.queryFamilyScore(familyId, daystamp);
			myFamilyScore = familyDriveDayStat2.getScore().toString();
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
			data.put("myFamilyId", String.valueOf(familyId));
			data.put("fightFamilyId", String.valueOf(fightFamilyId));
			data.put("fightFamilyScore", fightFamilyScore);
			data.put("fightFamilyName", fightFamilyName);
			data.put("fightFamilyImg", super.getImgBasePath() + fightFamilyImg);
			result.add(data);
		}
		rtMap.put("result", result);
		return ResultUtils.rtSuccess(rtMap);
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
		Map<String, Object> result = new HashMap<>();
		long myFamilyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		String daystamp = requestBodyParams.get("fightingTime").toString();
		LOG.info("myFamilyId=================" + myFamilyId);
		LOG.info("daystamp=================" + daystamp);
		result = this.gameServiceV2.getFightingHistory(myFamilyId, daystamp, result);
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
		String[] pages = { IndexSlipPage.ONE, IndexSlipPage.TWO, IndexSlipPage.THREE, IndexSlipPage.FOUR };
		map.put("pages", pages);
		return ResultUtils.rtSuccess(map);
	}

	/**
	 * 
	 * @Title: queryCompetitorFamilyInfo
	 * @Description: TODO(获取赛车场双方家族名字、分数)覆盖原Game Controller中的该方法
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/game/queryCompetitorFamilyInfo")
	public Map<String, Object> queryCompetitorFamilyInfo(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> myFamilyInfo = new HashMap<>();
		Map<String, Object> fightFamilyInfo = new HashMap<>();
		Map<String, Object> rtMap = new HashMap<>();
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		Long opponentId = fightService.getOpponentIdBySelfId(familyId, DateTools.getYYYY_MM_DD());
		LOG.info("familyId=================" + familyId);
		LOG.info("opponentId=================" + opponentId);
		myFamilyInfo.put("familyId", String.valueOf(familyId));
		myFamilyInfo.put("familyName", String.valueOf(familyService.findFamilyByFamilyId(familyId).get("familyName")));
		myFamilyInfo.put("familyScore", String.valueOf(familyScoreService.familyScore(familyId, getCurrentDayStr())));
		if (opponentId == null || opponentId == 0) {
			fightFamilyInfo = null;
		} else {
			fightFamilyInfo.put("familyId", String.valueOf(opponentId));
			fightFamilyInfo.put("familyName",
					String.valueOf(familyService.findFamilyByFamilyId(opponentId).get("familyName")));
			fightFamilyInfo.put("familyScore",
					String.valueOf(familyScoreService.familyScore(opponentId, getCurrentDayStr())));
		}
		rtMap.put("myFamilyInfo", myFamilyInfo);
		rtMap.put("fightFamilyInfo", fightFamilyInfo);
		return ResultUtils.rtSuccess(rtMap);
	}

}
