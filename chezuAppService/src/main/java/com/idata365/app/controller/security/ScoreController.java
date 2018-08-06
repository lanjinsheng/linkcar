package com.idata365.app.controller.security;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.DicFamilyTypeConstant;
import com.idata365.app.entity.CompetitorFamilyInfoResultBean;
import com.idata365.app.entity.CompetitorResultBean;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.FamilyCompetitorResultBean;
import com.idata365.app.entity.FamilyMemberAllResultBean;
import com.idata365.app.entity.GameHistoryResultBean;
import com.idata365.app.entity.GameResultWithFamilyResultBean;
import com.idata365.app.entity.ScoreByDayResultBean;
import com.idata365.app.entity.ScoreDetailUnitBean;
import com.idata365.app.entity.ScoreFamilyDetailResultBean;
import com.idata365.app.entity.ScoreFamilyInfoAllBean;
import com.idata365.app.entity.ScoreFamilyInfoBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.ScoreFamilyOrderResultBean;
import com.idata365.app.entity.ScoreMemberInfoResultBean;
import com.idata365.app.entity.ScoreUserHistoryParamBean;
import com.idata365.app.entity.ScoreUserHistoryResultAllBean;
import com.idata365.app.entity.SimulationScoreResultBean;
import com.idata365.app.entity.TravelDetailResultBean;
import com.idata365.app.entity.UserDetailResultBean;
import com.idata365.app.entity.UserTravelHistoryResultBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.YesterdayContributionResultBean;
import com.idata365.app.entity.YesterdayScoreResultBean;
import com.idata365.app.remote.ChezuService;
import com.idata365.app.service.FamilyInviteService;
import com.idata365.app.service.LotteryService;
import com.idata365.app.service.ScoreService;
import com.idata365.app.service.common.FamilyScoreService;
import com.idata365.app.serviceV2.GameServiceV2;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class ScoreController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(ScoreController.class);

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private ChezuService chezuService;

	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private GameServiceV2 gameServiceV2;

	@Autowired
	private FamilyScoreService familyScoreService;
	
	@Autowired
	private FamilyInviteService familyInviteService;
	
	

	/**
	 * 发起俱乐部、参与俱乐部查询
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/queryFamily")
	public Map<String, Object> queryFamily(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));

		ScoreFamilyInfoAllBean resultBean = this.scoreService.queryFamily(bean);

		String imgBasePath = super.getImgBasePath();

		ScoreFamilyInfoBean joinFamily = resultBean.getJoinFamily();
		if (null != joinFamily) {
			String imgUrl = joinFamily.getImgUrl();
			if (StringUtils.isNotBlank(imgUrl)) {
				joinFamily.setImgUrl(imgBasePath + imgUrl);
			}
		}

		ScoreFamilyInfoBean oriFamily = resultBean.getOriFamily();
		if (null != oriFamily) {
			String imgUrl = oriFamily.getImgUrl();
			if (StringUtils.isNotBlank(imgUrl)) {
				oriFamily.setImgUrl(imgBasePath + imgUrl);
			}
		}

		List<ScoreFamilyInfoAllBean> resultList = new ArrayList<ScoreFamilyInfoAllBean>();
		resultList.add(resultBean);

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 今日上榜俱乐部
	 * 
	 * @return
	 */
	@RequestMapping("/score/listFamily")
	public Map<String, Object> listFamily() {
		List<ScoreFamilyOrderResultBean> resultList = this.scoreService.queryFamilyOrderInfo();
		String imgBasePath = super.getImgBasePath();
		for (ScoreFamilyOrderResultBean tempBean : resultList) {
			String imgUrl = tempBean.getImgUrl();
			if (StringUtils.isNoneBlank(imgUrl)) {
				tempBean.setImgUrl(imgBasePath + imgUrl);
			}
		}
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 查看俱乐部信息
	 * 
	 * @return
	 */
	@RequestMapping("/score/queryFamilyDetail")
	public Map<String, Object> queryFamilyDetail(@RequestBody ScoreFamilyInfoParamBean bean) {
		bean.setUserId(this.getUserId());
		LOG.info("param==={}", JSON.toJSONString(bean));
		ScoreFamilyDetailResultBean resultBean = this.scoreService.queryFamilyDetail(bean);

		String imgBasePath = super.getImgBasePath();
		String imgUrl = resultBean.getImgUrl();
		if (StringUtils.isNotBlank(imgUrl)) {
			resultBean.setImgUrl(imgBasePath + imgUrl);
		}

		List<ScoreFamilyDetailResultBean> resultList = new ArrayList<>();
		String daystamp = null;
		Map<String, String> infoFamily = gameServiceV2.getInfoByFamilyId(bean.getFamilyId(), daystamp);
		resultBean.setTrophyNum(infoFamily.get("trophyNum"));
		resultBean.setGrade(infoFamily.get("gradeOrNum"));
		//是否申请过
		resultBean.setIsApplied(String.valueOf(familyInviteService.queryIsApplied(this.getUserId(), bean.getFamilyId())));
		//是否能申请
		resultBean.setIsCanApply(String.valueOf(familyInviteService.queryIsCanApply(bean.getFamilyId())));//Integer.valueOf(infoFamily.get("familyType"))
		resultBean.setClubLevelImg(DicFamilyTypeConstant.getDicFamilyType(Integer.valueOf(infoFamily.get("familyType"))).getIconUrl());
		
		resultList.add(resultBean);
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 查看俱乐部成员
	 * 
	 * @return
	 */
	@RequestMapping("/score/listFamilyMember")
	public Map<String, Object> listFamilyMember(@RequestBody ScoreFamilyInfoParamBean bean) {
		bean.setUserId(this.getUserId());
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ScoreMemberInfoResultBean> resultList = this.scoreService.listFamilyMember(bean);
		String imgBasePath = super.getImgBasePath();
		for (ScoreMemberInfoResultBean tempBean : resultList) {
			String imgUrl = tempBean.getImgUrl();
			tempBean.setImgUrl(imgBasePath + imgUrl);
		}
		Map<String, String> fightInfo = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		result.put("memberList", resultList);

		long myFamilyId = bean.getFamilyId();
		String fightingTime = null;
		CompetitorFamilyInfoResultBean resultBean = this.gameServiceV2.queryCompetitorFamilyInfo(myFamilyId,
				fightingTime);
		String daystamp = null;
		Map<String, String> infoFamily = gameServiceV2.getInfoByFamilyId(myFamilyId, daystamp);
		fightInfo.put("familyName", infoFamily.get("name"));
		double sc = familyScoreService.familyScore(Long.valueOf(myFamilyId), getCurrentDayStr());
		BigDecimal b = new BigDecimal(sc);
		fightInfo.put("familyScore", b.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
		if (null == resultBean) {
			fightInfo.put("fightFamilyName", "链车教官");
			double score = sc*0.8;
			fightInfo.put("fightFamilyScore", BigDecimal.valueOf(score).setScale(1, BigDecimal.ROUND_HALF_UP).toString());
		}else {
			long fightFamilyId = Long.valueOf(resultBean.getCompetitorFamilyId());
			Map<String, String> infoFamily2 = gameServiceV2.getInfoByFamilyId(fightFamilyId, daystamp);
			fightInfo.put("fightFamilyName", infoFamily2.get("name"));
			double scf = familyScoreService.familyScore(Long.valueOf(fightFamilyId), getCurrentDayStr());
			BigDecimal bf = new BigDecimal(scf);
			fightInfo.put("fightFamilyScore", bf.setScale(1, BigDecimal.ROUND_HALF_UP).toString());
		}
		result.put("fightInfo", fightInfo);
		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 历史得分（显示指定用户的）
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/listHistoryOrder")
	public Map<String, Object> listHistoryOrder(@RequestBody ScoreUserHistoryParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));

		ScoreUserHistoryResultAllBean resultBean = this.scoreService.listHistoryOrder(bean);
		return ResultUtils.rtSuccess(resultBean);
	}

	/**
	 * 历史驾驶得分
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/getScoreByDay")
	public Map<String, Object> getScoreByDay(@RequestBody ScoreUserHistoryParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));

		List<ScoreByDayResultBean> resultList = this.scoreService.getScoreByDay(bean);
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 昨日得分
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/findYesterdayScore")
	public Map<String, Object> findYesterdayScore(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));

		List<YesterdayScoreResultBean> resultList = this.scoreService.findYesterdayFamilyScore(bean);

		String imgBasePath = super.getImgBasePath();
		for (YesterdayScoreResultBean tempBean : resultList) {
			String imgUrl = tempBean.getImgUrl();
			if (StringUtils.isNotBlank(imgUrl)) {
				tempBean.setImgUrl(imgBasePath + imgUrl);
			}
		}

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 族内贡献榜(昨日得分)
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/familyContribution")
	public Map<String, Object> familyContribution(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));

		List<YesterdayContributionResultBean> resultList = this.scoreService.familyContribution(bean);

		String imgBasePath = super.getImgBasePath();
		for (YesterdayContributionResultBean tempBean : resultList) {
			String imgUrl = tempBean.getImgUrl();
			if (StringUtils.isNotBlank(imgUrl)) {
				tempBean.setImgUrl(imgBasePath + imgUrl);
			}
		}

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 昨日赛果
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showGameResult")
	public Map<String, Object> showGameResult(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));

		List<CompetitorResultBean> resultList = new ArrayList<>();

		CompetitorResultBean resultBean = this.scoreService.showGameResult(bean);

		if (null != resultBean) {
			String imgBasePath = super.getImgBasePath();

			FamilyCompetitorResultBean gameObj = resultBean.getGameObj();
			FamilyCompetitorResultBean competitorObj = resultBean.getCompetitorObj();
			// 对于首日刚创建的用户,无昨日数据，进行特殊初始化处理
			if (gameObj == null || competitorObj == null) {
				// 判断用户创建时间可能为今日，无昨日赛况信息，造成数据无效，需要进行初始化数据返回
				scoreService.dealGameResultInit(resultBean, bean.getFamilyId());
				gameObj = resultBean.getGameObj();
				competitorObj = resultBean.getCompetitorObj();
			}

			if (gameObj != null && StringUtils.isNotBlank(gameObj.getImgUrl())) {
				gameObj.setImgUrl(imgBasePath + gameObj.getImgUrl());
			}

			if (null != competitorObj && StringUtils.isNotBlank(competitorObj.getImgUrl())) {
				competitorObj.setImgUrl(imgBasePath + competitorObj.getImgUrl());
			}

			resultList.add(resultBean);
		}

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 显示当天行程
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showTravels")
	public Map<String, Object> showTravels(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<UserTravelHistoryResultBean> resultList = this.scoreService.showTravels(bean);

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showTravelDetail")
	public Map<String, Object> showTravelDetail(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		// Map<String, Object> gpsMap = getGps(bean.getUserId(), bean.getHabitId());
		List<TravelDetailResultBean> resultList = this.scoreService.showTravelDetail(bean);

		// if (CollectionUtils.isNotEmpty(resultList))
		// {
		// //设置GPS
		// resultList.get(0).setGpsMap(gpsMap);
		// //获取行程道具
		// List<Map<String,String>>
		// userTravelLotterys=lotteryService.getUserTravelLotterys(bean.getUserId(),
		// bean.getHabitId());
		// resultList.get(0).setUserTravelLotterys(userTravelLotterys);
		// }

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showTravelDetailGps")
	public Map<String, Object> showTravelDetailGps(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		Map<String, Object> rtGps = new HashMap<String, Object>();
		Map<String, Object> gpsMap = getGps(bean.getUserId(), bean.getHabitId());
		// 设置GPS
		rtGps.put("gpsMap", gpsMap);
		// 获取行程道具
		List<Map<String, String>> userTravelLotterys = lotteryService.getUserTravelLotterys(bean.getUserId(),
				bean.getHabitId());
		rtGps.put("userTravelLotterys", userTravelLotterys);
		return ResultUtils.rtSuccess(rtGps);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showTravelLotterys")
	public Map<String, Object> showTravelLotterys(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		Map<String, Object> rtGps = new HashMap<String, Object>();
		// 获取行程道具
		List<Map<String, String>> userTravelLotterys = lotteryService.getUserTravelLotterys(bean.getUserId(),
				bean.getHabitId());
		rtGps.put("userTravelLotterys", userTravelLotterys);
		return ResultUtils.rtSuccess(rtGps);
	}

	public Map<String, Object> getGps(long userId, long habitId) {
		String args = userId + "" + habitId;
		String sign = SignUtils.encryptHMAC(args);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("habitId", habitId);
		map.put("sign", sign);
		Map<String, Object> datas = chezuService.getGpsByUH(map);

		// String args=28+""+1514160360;
		// String sign=SignUtils.encryptHMAC(args);
		// Map<String,Object> map=new HashMap<String,Object> ();
		// map.put("userId", 28);
		// map.put("habitId", 1514160360);
		// map.put("sign", sign);
		// Map<String,Object> datas=chezuService.getGpsByUH(map);

		return datas;

	}

	/**
	 * 昨日赛果-俱乐部成员详情
	 * 
	 * @param bean
	 * @return
	 */
	// temp settings
	@RequestMapping("/score/showGameResultWithFamily")
	public Map<String, Object> showGameResultWithFamily(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<GameResultWithFamilyResultBean> resultList = this.scoreService.showGameResultWithFamily(bean);
		return ResultUtils.rtSuccess(resultList);
	}

	@RequestMapping("/score/scoreDetail")
	public Map<String, Object> scoreDetail(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ScoreDetailUnitBean> resultList = this.scoreService.scoreDetail(bean);
		return ResultUtils.rtSuccess(resultList);
	}

	@RequestMapping("/score/gameHistory")
	public Map<String, Object> gameHistory(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<GameHistoryResultBean> resultList = this.scoreService.gameHistory(bean);
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 查看玩家信息
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/score/showUserDetail")
	public Map<String, Object> showUserDetail(@RequestBody ScoreFamilyInfoParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		String imgBasePath = super.getImgBasePath();
		UserDetailResultBean resultBean = this.scoreService.showUserDetail(bean);

		String userImgUrl = resultBean.getUserImgUrl();
		if (StringUtils.isNotBlank(userImgUrl)) {
			resultBean.setUserImgUrl(imgBasePath + userImgUrl);
		}

		String familyImgUrl = resultBean.getFamilyImgUrl();
		if (StringUtils.isNotBlank(familyImgUrl)) {
			resultBean.setFamilyImgUrl(imgBasePath + familyImgUrl);
		}

		return ResultUtils.rtSuccess(resultBean);
	}

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
}
