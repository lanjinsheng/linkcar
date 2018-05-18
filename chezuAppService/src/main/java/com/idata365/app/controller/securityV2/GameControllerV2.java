package com.idata365.app.controller.securityV2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.CompetitorFamilyInfoResultBean;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.FamilyDriveDayStat;
import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.DicService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.common.FamilyScoreService;
import com.idata365.app.serviceV2.GameServiceV2;
import com.idata365.app.serviceV2.ScoreServiceV2;
import com.idata365.app.serviceV2.UserInfoServiceV2;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class GameControllerV2 extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(GameControllerV2.class);

	@Autowired
	private GameServiceV2 gameService;
	@Autowired
	private ScoreServiceV2 scoreService;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private UserInfoServiceV2 userInfoService;
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
	@RequestMapping("/v2/getIndexFightInfo")
	public Map<String, Object> getIndexFightInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setUserId(userId);
		ScoreFamilyInfoAllBean queryFamily = scoreService.queryFamily(bean);
		List<Map<String, String>> infoList = new ArrayList<>();
		if (ValidTools.isBlank(queryFamily)) {
			return ResultUtils.rtSuccess(null);
		}
		if (ValidTools.isNotBlank(queryFamily.getOriFamily())) {
			String familyId = String.valueOf(queryFamily.getOriFamily().getFamilyId());
			Map<String, String> map = new HashMap<>();
			map.put("familyId", familyId);
			map.put("familyName", queryFamily.getOriFamily().getName());
			map.put("familyScore",
					familyScoreService.familyScore(Long.valueOf(familyId), getCurrentDayStr()).toString());
			String fightingTime = null;
			CompetitorFamilyInfoResultBean resultBean = this.gameService
					.queryCompetitorFamilyInfo(Long.valueOf(familyId), fightingTime);
			if (null != resultBean) {
				long fightFamilyId = Long.valueOf(resultBean.getCompetitorFamilyId());
				Map<String, Object> fightFamily = familyService.findFamilyByFamilyId(fightFamilyId);
				// 分数获取中。。。
				map.put("fightFamilyId", String.valueOf(fightFamilyId));
				map.put("fightFamilyName", fightFamily.get("nikeName").toString());
				map.put("fightFamilyScore",
						familyScoreService.familyScore(Long.valueOf(fightFamilyId), getCurrentDayStr()).toString());
				infoList.add(map);
			}
		}
		if (ValidTools.isNotBlank(queryFamily.getJoinFamily())) {
			Map<String, String> map = new HashMap<>();
			String familyId = String.valueOf(queryFamily.getJoinFamily().getFamilyId());
			map.put("familyId", familyId);
			map.put("familyName", queryFamily.getJoinFamily().getName());
			map.put("familyScore",
					familyScoreService.familyScore(Long.valueOf(familyId), getCurrentDayStr()).toString());
			String fightingTime = null;
			CompetitorFamilyInfoResultBean resultBean = this.gameService
					.queryCompetitorFamilyInfo(Long.valueOf(familyId), fightingTime);
			if (null == resultBean) {
				return ResultUtils.rtSuccess(null);
			}
			long fightFamilyId = Long.valueOf(resultBean.getCompetitorFamilyId());
			// 分数获取中。。。
			Map<String, Object> fightFamily = familyService.findFamilyByFamilyId(fightFamilyId);
			// 分数获取中。。。
			map.put("fightFamilyId", String.valueOf(fightFamilyId));
			map.put("fightFamilyName", fightFamily.get("nikeName").toString());
			map.put("fightFamilyScore",
					familyScoreService.familyScore(Long.valueOf(fightFamilyId), getCurrentDayStr()).toString());
			infoList.add(map);
		}

		return ResultUtils.rtSuccess(infoList);
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
	@RequestMapping("/v2/billBoard")
	public Map<String, Object> billBoard(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		String billBoardType = requestBodyParams.get("billBoardType").toString();
		String sign = SignUtils.encryptHMAC(String.valueOf(billBoardType));
		List<Map<String, String>> billList = new ArrayList<>();
		if ("1".equals(billBoardType)) {
			billList = gameService.billBoard();
		} else if ("2".equals(billBoardType) || "3".equals(billBoardType)) {
			billList = chezuAssetService.billBoard(billBoardType, sign);
			if (ValidTools.isNotBlank(billList)) {
				for (int i = 0; i < billList.size(); i++) {
					long id = Long.valueOf(billList.get(i).get("userId"));
					UsersAccount account = userInfoService.getUsersAccount(id);
					billList.get(i).put("name", account.getNickName());
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
	@RequestMapping("/v2/fightingDetail")
	public Map<String, Object> fightingDetail(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		long myFamilyId = Long.valueOf(requestBodyParams.get("myFamilyId").toString());
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
			familyInfo.put("familyScore",
					familyScoreService.familyScore(Long.valueOf(arr[i]), getCurrentDayStr()).toString());
			familyInfo.put("trophyNum", infoFamily.get("trophyNum"));
			familyInfo.put("grade", infoFamily.get("gradeOrNum"));

			ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
			bean.setFamilyId(arr[i]);
			List<ScoreMemberInfoResultBean> member = scoreService.listFamilyMember(bean);
			List<Map<String, String>> memberScoreS = new ArrayList<>();
			for (int j = 0; j < member.size(); j++) {
				Map<String, String> memberScore = new HashMap<>();
				String memberId = member.get(j).getUserId();
				double score = scoreService.getAvgScore(memberId, myFamilyId);
				UsersAccount account = userInfoService.getUsersAccount(Long.valueOf(memberId));
				String name = account.getNickName();
				memberScore.put("name", name);
				memberScore.put("score", String.valueOf(score));
				memberScoreS.add(memberScore);
			}
			familyInfo.put("memberScoreS", memberScoreS);

			data.add(familyInfo);

			if (i == 0) {
				// 我的家族等级
				String familyTypeValue = infoFamily.get("gradeOrNum");
				String showInfo = "赛季结束后钻石段位玩家可获得：1200钻石";
				if (familyTypeValue.contains("钻石")) {
					showInfo = "当前段位赛季结束后可获得：1200钻石";
				}
				String win = null;
				String loss = null;
				List<DicFamilyType> types = dicService.getDicFamilyType();
				for (DicFamilyType type : types) {
					if (type.getFamilyTypeValue().equals(familyTypeValue)) {
						win = "奖杯+" + type.getWin() + "\t钻石+30";
						loss = "奖杯-" + type.getLoss();
					}
				}
				result.put("reward", win);
				result.put("punishment", loss);
				result.put("surPlusDays", dicService.getSurPlusDays());
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
	 * @Description: TODO(对战详情)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/v2/fightingRecord")
	public Map<String, Object> fightingRecord(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		long familyId = Long.valueOf(requestBodyParams.get("myFamilyId").toString());
		long recordId = Long.valueOf(requestBodyParams.get("recordId").toString());
		ScoreFamilyInfoParamBean bean = new ScoreFamilyInfoParamBean();
		bean.setFamilyId(familyId);
		ScoreFamilyDetailResultBean familyDetail = scoreService.queryFamilyDetail(bean);
		Map<String, Object> map = new HashMap<>();
		String daystam = null;
		Map<String, String> infoFamily = gameService.getInfoByFamilyId(familyId, daystam);
		map.put("familyName", familyDetail.getFamilyName());
		map.put("rank", familyDetail.getOrderNo());
		map.put("trophyNum", infoFamily.get("gradeOrNum"));
		map.put("familyImg", familyDetail.getImgUrl());
		map.put("grade", infoFamily.get("gradeOrNum"));
		long fightFamilyId;
		List<FamilyRelation> recordList = gameService.queryFightRecordByFamilyId(Long.valueOf(familyId));
		List<Map<String, String>> result = new ArrayList<>();
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
			FamilyDriveDayStat familyDriveDayStat = gameService.queryFamilyScore(fightFamilyId, daystamp);
			String fightFamilyScore = familyDriveDayStat.getScore().toString();
			FamilyDriveDayStat familyDriveDayStat2 = gameService.queryFamilyScore(familyId, daystamp);
			String myFamilyScore = familyDriveDayStat2.getScore().toString();
			String status;
			if (Long.valueOf(myFamilyScore) > Long.valueOf(fightFamilyScore)) {
				status = "1";
			} else if (Long.valueOf(myFamilyScore) < Long.valueOf(fightFamilyScore)) {
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
			data.put("fightFamilyImg", fightFamilyImg);
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
	@RequestMapping("/v2/fightingHistoryScore")
	public Map<String, Object> fightingHistoryScore(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		long myFamilyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		String daystamp = requestBodyParams.get("fightingTime").toString();
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
				String score = user.get(j).get("score").toString();
				UsersAccount account = userInfoService.getUsersAccount(Long.valueOf(memberId));
				String name = account.getNickName();
				memberScore.put("name", name);
				memberScore.put("score", score);
				memberScoreS.add(memberScore);
			}

			familyInfo.put("memberScoreS", memberScoreS);

			data.add(familyInfo);

			if (i == 0) {
				// 我的家族等级
				String familyTypeValue = infoFamily.get("gradeOrNum");

				List<DicFamilyType> types = dicService.getDicFamilyType();
				for (DicFamilyType type : types) {
					if (type.getFamilyTypeValue().equals(familyTypeValue)) {
						win = "奖杯+" + type.getWin() + "\t钻石+30";
						loss = "奖杯-" + type.getLoss();
					}
				}

			}
		}
		result.put("surPlusDays", 0);
		result.put("myFamilyInfo", data.get(0));
		result.put("fightFamilyInfo", data.get(1));
		if (Long.valueOf(data.get(0).get("familyScore").toString()) > Long
				.valueOf(data.get(1).get("familyScore").toString())) {
			result.put("rewardAndPunishment", win);
		} else if (Long.valueOf(data.get(0).get("familyScore").toString()) < Long
				.valueOf(data.get(1).get("familyScore").toString())) {
			result.put("rewardAndPunishment", loss);
		} else {
			result.put("rewardAndPunishment", "和气生财！！！");
		}

		return ResultUtils.rtSuccess(result);
	}

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
}
