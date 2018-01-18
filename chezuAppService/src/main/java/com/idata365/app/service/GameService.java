package com.idata365.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.FamilyConstant;
import com.idata365.app.constant.LotteryConstant;
import com.idata365.app.constant.ResultConstant;
import com.idata365.app.entity.CompetitorFamilyInfoResultBean;
import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyInfoResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.JudgeChallengeResultBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.PenalResultBean;
import com.idata365.app.entity.ReadyLotteryBean;
import com.idata365.app.entity.ReadyLotteryResultBean;
import com.idata365.app.entity.RoleCountBean;
import com.idata365.app.entity.RoleCountResultBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.StationBean;
import com.idata365.app.entity.StationResultBean;
import com.idata365.app.entity.SwitchLotteryParamBean;
import com.idata365.app.entity.TravelHistoryParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserScoreDayParamBean;
import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;
import com.idata365.app.entity.ViolationStatResultAllBean;
import com.idata365.app.entity.ViolationStatResultBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.NumUtils;
import com.idata365.app.util.RandUtils;

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;

@Service
public class GameService extends BaseService<GameService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
	
	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private FamilyMapper familyMapper;
	
	@Autowired
	private LotteryMapper lotteryMapper;
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 违规情况
	 * @param bean
	 * @return
	 */
	public ViolationStatResultAllBean violationList(ViolationStatParamBean bean)
	{
		ViolationStatResultAllBean resultAllBean = new ViolationStatResultAllBean();
		
		Date todayDate = Calendar.getInstance().getTime();
		String todayStr = DateFormatUtils.format(todayDate, DateConstant.DAY_PATTERN_DELIMIT);
		bean.setDaystamp(todayStr);
		
		ViolationStatBean myBean = this.gameMapper.queryFamilyDriveDayStat(bean);
		ViolationStatResultBean myResultBean = new ViolationStatResultBean();
		AdBeanUtils.copyOtherPropToStr(myResultBean, myBean);
		resultAllBean.setMyBean(myResultBean);
		
		long myFamilyId = bean.getFamilyId();
		FamilyRelationBean familyRelationParamBean = new FamilyRelationBean();
		familyRelationParamBean.setFamilyId(myFamilyId);
		familyRelationParamBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelationBean> relationList = this.familyMapper.queryFamilyIdByCompetitorId(familyRelationParamBean);
		if (CollectionUtils.isNotEmpty(relationList))
		{
			FamilyRelationBean tempRelationBean = relationList.get(0);
			long familyId1 = tempRelationBean.getFamilyId1();
			if (myFamilyId != familyId1)
			{
				bean.setFamilyId(familyId1);
			}
			else
			{
				long familyId2 = tempRelationBean.getFamilyId2();
				bean.setFamilyId(familyId2);
			}
			
			ViolationStatBean competitorBean = this.gameMapper.queryFamilyDriveDayStat(bean);
			ViolationStatResultBean competitorResultBean = new ViolationStatResultBean();
			AdBeanUtils.copyOtherPropToStr(competitorResultBean, competitorBean);
			resultAllBean.setCompetitorBean(competitorResultBean);
		}
		
		return resultAllBean;
	}
	
	/**
	 * 1表成功；0表失败
	 * @param bean
	 * @return
	 */
	@Transactional
	public int challengeFamily(GameFamilyParamBean bean)
	{
		String tomorrowDateStr = getTomorrowDateStr();
		
		FamilyChallengeLogParamBean saveChallgenParamBean = new FamilyChallengeLogParamBean();
		saveChallgenParamBean.setFamilyId(bean.getFamilyId());
		saveChallgenParamBean.setChallengeType(bean.getFamilyType());
		saveChallgenParamBean.setChallengeDay(tomorrowDateStr);
		
		//判断是否已经发起挑战
		boolean challengeFlag = judgeChallenged(saveChallgenParamBean);
		if (challengeFlag)
		{
			return 0;
		}
		
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(bean.getFamilyId());
		//查询发起挑战的家族信息
		FamilyInfoBean familyInfoBean = this.familyMapper.queryFamilyInfo(familyParamBean);
		
		//被挑战家族类型的总数
		familyParamBean.setFamilyType(bean.getFamilyType());
		int allFamilyTypeCount = this.familyMapper.countByType(familyParamBean);
		
		//指定类型家族已经被挑战的数量
		FamilyChallengeLogParamBean beChallengedLogParamBean = new FamilyChallengeLogParamBean();
		beChallengedLogParamBean.setChallengeType(bean.getFamilyType());
		beChallengedLogParamBean.setChallengeDay(tomorrowDateStr);
		int tomorrowBeChallengedCount = this.gameMapper.countBeChallenge(beChallengedLogParamBean);
		
		//指定类型家族已经发起挑战的数量
		FamilyChallengeLogParamBean challengeLogParamBean = new FamilyChallengeLogParamBean();
		challengeLogParamBean.setPrevType(bean.getFamilyType());
		challengeLogParamBean.setChallengeDay(tomorrowDateStr);
		int tomorrowChallengeCount = this.gameMapper.countChallenge(challengeLogParamBean);
		
		//如果指定家族类型的总数大于(这个类型家族挑战别人和被别人挑战数量的和)，则挑战成功
		if (allFamilyTypeCount > tomorrowBeChallengedCount + tomorrowChallengeCount)
		{
			if (bean.getFamilyType() == familyInfoBean.getFamilyType())
			{
				if (allFamilyTypeCount - 1 == tomorrowBeChallengedCount + tomorrowChallengeCount)
				{
					return 0;
				}
			}
			
			saveChallgenParamBean.setPrevType(familyInfoBean.getFamilyType());
			//记录挑战家族日志 
			this.gameMapper.saveChallengeLog(saveChallgenParamBean);
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	//判断是否已经成功发起过挑战
	public boolean judgeChallenged(FamilyChallengeLogParamBean bean)
	{
		int count = this.gameMapper.countChallengeByFamilyId(bean);
		if (count > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public JudgeChallengeResultBean judgeChallengeFlag(GameFamilyParamBean bean)
	{
		long prevFamilyId = bean.getFamilyId();
		
		FamilyChallengeLogParamBean familyChallengeLogParamBean = new FamilyChallengeLogParamBean();
		familyChallengeLogParamBean.setFamilyId(prevFamilyId);
		familyChallengeLogParamBean.setChallengeDay(getTomorrowDateStr());
		
		JudgeChallengeResultBean resultBean = new JudgeChallengeResultBean();
		if (judgeChallenged(familyChallengeLogParamBean))
		{
			FamilyRelationBean relationBean = new FamilyRelationBean();
			relationBean.setFamilyId(prevFamilyId);
			relationBean.setDaystamp(getTomorrowDateStr());
			
			FamilyRelationBean relationResultBean = this.familyMapper.queryFamilyIdByCompetitorId(relationBean).get(0);
			
			if (null != relationResultBean)
			{
				long familyId1 = relationResultBean.getFamilyId1();
				long familyId2 = relationResultBean.getFamilyId2();
				
				long dstFamilyId;
				if (prevFamilyId != familyId1)
				{
					dstFamilyId = familyId1;
				}
				else
				{
					dstFamilyId = familyId2;
				}
				
				FamilyParamBean familyParamBean = new FamilyParamBean();
				familyParamBean.setFamilyId(dstFamilyId);
				FamilyInfoBean tempFamilyInfoBean = this.familyMapper.queryFamilyInfo(familyParamBean);
				
				FamilyInfoResultBean familyObj = new FamilyInfoResultBean();
				familyObj.setFamilyId(String.valueOf(tempFamilyInfoBean.getId()));
				familyObj.setFamilyName(tempFamilyInfoBean.getFamilyName());
				familyObj.setFamilyType(String.valueOf(tempFamilyInfoBean.getFamilyType()));
				familyObj.setImgUrl(tempFamilyInfoBean.getImgUrl());
				
				resultBean.setFamilyObj(familyObj);
			}
			else
			{
				resultBean.setChallengeFlag("CHALLENGE");
				
				FamilyChallengeLogParamBean tempParamTypeBean = new FamilyChallengeLogParamBean();
				tempParamTypeBean.setFamilyId(prevFamilyId);
				tempParamTypeBean.setChallengeDay(getTomorrowDateStr());
				FamilyChallengeLogBean tempTypeResultBean = this.gameMapper.queryChallengeType(tempParamTypeBean);
				int challengeType = tempTypeResultBean.getChallengeType();
				
				String msgTemplate = "您已成功挑战FAMILY_HOLD家族\\n中午12：00将公布您的竞争对手，敬请期待！";
				String tempMsg;
				if (FamilyConstant.BRONZE_TYPE == challengeType)
				{
					tempMsg = StringUtils.replace(msgTemplate, "FAMILY_HOLD", "青铜");
				}
				else if (FamilyConstant.SILVER_TYPE == challengeType)
				{
					tempMsg = StringUtils.replace(msgTemplate, "FAMILY_HOLD", "白银");
				}
				else
				{
					tempMsg = StringUtils.replace(msgTemplate, "FAMILY_HOLD", "黄金");
				}
				
				resultBean.setChallengeMsg(tempMsg);
			}
		}
		else
		{
			resultBean.setChallengeFlag("UN_CHALLENGE");
			resultBean.setChallengeMsg("未发起挑战");
		}
		
		return resultBean;
	}
	
	//查询正在对战的家族系信息
	public CompetitorFamilyInfoResultBean queryCompetitorFamilyInfo(GameFamilyParamBean bean)
	{
		FamilyRelationBean relationBean = new FamilyRelationBean();
		long myFamilyId = bean.getFamilyId();
		relationBean.setFamilyId(myFamilyId);
		relationBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelationBean> competitorList = this.familyMapper.queryFamilyIdByCompetitorId(relationBean);
		if (CollectionUtils.isEmpty(competitorList))
		{
			return null;
		}
		
		FamilyRelationBean relationResultBean = competitorList.get(0);
		long familyId1 = relationResultBean.getFamilyId1();
		long familyId2 = relationResultBean.getFamilyId2();
		
		FamilyParamBean familyParamBean = new FamilyParamBean();
		if (myFamilyId != familyId1)
		{
			familyParamBean.setFamilyId(familyId1);
		}
		else
		{
			familyParamBean.setFamilyId(familyId2);
		}
		
		FamilyInfoBean familyInfoBean = this.familyMapper.queryFamilyInfo(familyParamBean);

		if (null == familyInfoBean)
		{
			return null;
		}
		
		CompetitorFamilyInfoResultBean resultBean = new CompetitorFamilyInfoResultBean();
		resultBean.setCompetitorFamilyId(String.valueOf(familyInfoBean.getId()));
		resultBean.setFamilyName(familyInfoBean.getFamilyName());
		resultBean.setImgUrl(familyInfoBean.getImgUrl());
		resultBean.setCountdown(String.valueOf(calDown()));
		
		return resultBean;
	}
	
	public static long calDown()
	{
		try
		{
			long calCountDown = calCountDown();
			return calCountDown;
		} catch (ParseException e)
		{
			LOGGER.error("", e);
		}
		
		return 0;
	}
	
	private static final String ZERO_POINT = "00:00:00";
	
	private static final String TEN_POINT = "10:00:00";
	
	private static final String SIXTEEN_POINT = "16:00:00";

	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static long calCountDown() throws ParseException
	{
		Date currentTs = Calendar.getInstance().getTime();
		String prefixDayStr = DateFormatUtils.format(currentTs, "yyyy-MM-dd");
		
		Date tomorrowDate = DateUtils.addDays(currentTs, 1);
		String tomorrowDayStr = DateFormatUtils.format(tomorrowDate, "yyyy-MM-dd");
		
		long currentSeconds = currentTs.getTime()/1000L;
		
		String zeroTsStr = prefixDayStr + " " + ZERO_POINT;
		String tenTsStr = prefixDayStr + " " + TEN_POINT;
		String sixteenTsStr = prefixDayStr + " " + SIXTEEN_POINT;
		
		String tomorrowTsTr = tomorrowDayStr + " " + ZERO_POINT;
		
		Date zeroTs = DateUtils.parseDate(zeroTsStr, DATE_PATTERN);
		Date tenTs = DateUtils.parseDate(tenTsStr, DATE_PATTERN);
		Date sixteenTs = DateUtils.parseDate(sixteenTsStr, DATE_PATTERN);
		
		Date tomorrowTs = DateUtils.parseDate(tomorrowTsTr, DATE_PATTERN);
		
		if (currentTs.compareTo(zeroTs) >= 0 && currentTs.compareTo(tenTs) < 0)
		{
			return tenTs.getTime()/1000 - currentSeconds;
		}
		else if (currentTs.compareTo(tenTs) >= 0 && currentTs.compareTo(sixteenTs) < 0)
		{
			return sixteenTs.getTime()/1000 - currentSeconds;
		}
		else
		{
			return tomorrowTs.getTime()/1000 - currentSeconds;
		}
	}
	
	@Transactional
	public PenalResultBean penalSpeed(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithSpeedPenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateSpeedPenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalBrake(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithBrakePenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateSpeedPenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalTurn(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithTurnPenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateTurnPenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalOverspeed(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithOverspeedPenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateOverspeedPenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalNightDrive(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithNightDrivePenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateNightDrivePenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalTiredDrive(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithTiredDrivePenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateTiredDrivePenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalIllegalStop(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (null == countLottery || countLottery == 0)
		{
			resultBean.setPenalStatus(ResultConstant.NONE_LEFT);
			return resultBean;
		}
		
		long familyId = bean.getFamilyId();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setFamilyId(familyId);
		String currentDayStr = getCurrentDayStr();
		familyParamBean.setDaystamp(currentDayStr);
		
		List<Long> userIds = this.familyMapper.queryUserIdsWithIllegalStopPenal(familyParamBean);
		if (CollectionUtils.isEmpty(userIds))
		{
			resultBean.setPenalStatus(ResultConstant.PENAL_OUT);
			return resultBean;
		}
		int idx = RandUtils.generateRand(0, userIds.size());
		
		FamilyParamBean penalBean = new FamilyParamBean();
		penalBean.setDaystamp(currentDayStr);
		penalBean.setUserId(userIds.get(idx));
		this.familyMapper.updateIllegalStopPenalTimes(penalBean);
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		ScoreFamilyInfoParamBean zhitiaoParamBean = new ScoreFamilyInfoParamBean();
		zhitiaoParamBean.setUserId(userIds.get(idx));
		zhitiaoParamBean.setDaystamp(currentDayStr);
		this.scoreMapper.updateUseZhitiao(zhitiaoParamBean);
		
		return resultBean;
	}
	
	/**
	 * 显示停车位
	 * @param bean
	 * @return
	 */
	public List<StationResultBean> listStations(GameFamilyParamBean bean)
	{
		FamilyRelationBean familyRelationBean = new FamilyRelationBean();
		long familyId = bean.getFamilyId();
		familyRelationBean.setFamilyId(familyId);
		familyRelationBean.setDaystamp(getCurrentDayStr());
		List<Long> relationIdList = this.familyMapper.queryFamilyRelationIds(familyRelationBean);
		if (CollectionUtils.isEmpty(relationIdList))
			return null;
		
		long familyRelationId = relationIdList.get(0);
		
		FamilyRelationParamBean familyRelationParamBean = new FamilyRelationParamBean();
		familyRelationParamBean.setFamilyRelationId(familyRelationId);
		List<StationBean> stationList = this.gameMapper.queryStations(familyRelationParamBean);
		
		List<StationResultBean> resultList = new ArrayList<>();
		for (StationBean tempBean : stationList)
		{
			StationResultBean tempResulBean = new StationResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResulBean, tempBean);
			
			long tempFamilyId = tempBean.getFamilyId();
			String status = tempBean.getStatus();
			if (tempFamilyId == familyId)
			{
				if (StringUtils.equals(status, "STOP"))
				{
					tempResulBean.setStatus("MINE_STOP");
				}
				else if (StringUtils.equals(status, "HOLD"))
				{
					tempResulBean.setStatus("MINE_HOLD");
				}
			}
			else
			{
				if (StringUtils.equals(status, "STOP"))
				{
					tempResulBean.setStatus("COMPETITOR_STOP");
				}
				else if (StringUtils.equals(status, "HOLD"))
				{
					tempResulBean.setStatus("COMPETITOR_HOLD");
				}
			}
			
			resultList.add(tempResulBean);
		}
		
		return resultList;
	}
	
	/**
	 * 停车到指定车位
	 * @param bean
	 */
	public int stopAtStation(GameFamilyParamBean bean)
	{
		int result = this.gameMapper.updateToStopParkStation(bean);
		return result;
	}
	
	/**
	 * 使用小马扎占车位
	 * @param bean
	 * @return
	 */
	@Transactional
	public int holdAtStation(GameFamilyParamBean bean)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(bean.getUserId());
		lotteryBean.setAwardId(LotteryConstant.MAZHA_LOTTERY);
		
		Integer countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		//马扎用光
		if (null == countLottery || countLottery == 0)
		{
			return -1;
		}
				
		int result = this.gameMapper.updateToHoldParkStation(bean);
		
		if (0 == result)
			return 0;
		
		//道具减1
		this.lotteryMapper.updateLotteryCount(lotteryBean);
		
		//记录使用小马扎数
		ScoreFamilyInfoParamBean paramHoldBean = new ScoreFamilyInfoParamBean();
		paramHoldBean.setUserId(bean.getUserId());
		paramHoldBean.setDaystamp(getCurrentDayStr());
		this.scoreMapper.updateUseHoldNum(paramHoldBean);
		
		return 1;
	}
	
	/**
	 * 通知家族内其他人去贴条
	 * @param bean
	 * @param userInfo
	 */
	public void informOtherToPenalty(GameFamilyParamBean bean, UserInfo userInfo)
	{
		List<Long> userIdList = this.gameMapper.queryFamilyOtherUserId(bean);
		for (Long tempUserId : userIdList)
		{
			dealtMsg(userInfo, null, tempUserId, MessageEnum.INFORM_PENALTY);
		}
	}
	
	private void dealtMsg(UserInfo userInfo, Long inviteId, Long toUserId, MessageEnum messageEnum)
	{
		LOGGER.info("userInfo={}", JSON.toJSONString(userInfo));
		LOGGER.info("inviteId={}\ttoUserId={}", inviteId, toUserId);
		LOGGER.info("messageEnum={}", messageEnum);
		
		//构建成员加入消息
  		Message message=messageService.buildMessage(userInfo.getId(), userInfo.getPhone(), userInfo.getNickName(), toUserId, inviteId, messageEnum);
  		//插入消息
  		messageService.insertMessage(message, messageEnum);
  		//推送消息
  		messageService.pushMessage(message, messageEnum);
	}
	
	/**
	 * 隐藏该段行程
	 * @param bean
	 * @return
	 */
	public int hiddenTravel(TravelHistoryParamBean bean)
	{
		return this.gameMapper.updateTravelHistoryHidden(bean);
	}
	
	private String getCurrentDayStrUnDelimiter()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN);
		return dayStr;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
	
	/**
	 * 切换明日角色
	 * @param bean
	 */
	@Transactional
	public void switchRole(UserFamilyRoleLogParamBean bean)
	{
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		int roleCount = this.gameMapper.countTomorrowRole(bean);
		if (roleCount > 0)
		{
			this.gameMapper.updateUserFamilyRole(bean);
		}
		else
		{
			String tomorrowDateStr = getTomorrowDateStr();
			String startTime = tomorrowDateStr + " 00:00:00";
			String endTime = tomorrowDateStr + " 23:59:59";
			bean.setStartTime(startTime);
			bean.setEndTime(endTime);
			
			this.gameMapper.saveUserFamilyRole(bean);
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public int findTomorrowRole(UserFamilyRoleLogParamBean bean)
	{
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		List<Integer> roleList = this.gameMapper.queryRoleByDay(bean);
		if (CollectionUtils.isNotEmpty(roleList))
		{
			return roleList.get(0);
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public int findTodayRole(UserFamilyRoleLogParamBean bean)
	{
		bean.setDaystamp(getTodayDateUndelimiterStr());
		List<Integer> roleList = this.gameMapper.queryRoleByDay(bean);
		if (CollectionUtils.isNotEmpty(roleList))
		{
			return roleList.get(0);
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * 装备道具
	 * @param bean
	 */
	@Transactional
	public List<ReadyLotteryResultBean> getReadyLottery(ReadyLotteryBean bean)
	{
		getReadyLotteryB(bean);
		List<ReadyLotteryResultBean> resultList = this.findTomorrowReadyLottery(bean);
		return resultList;
	}

	private void getReadyLotteryB(ReadyLotteryBean bean)
	{
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		this.lotteryMapper.saveOrUpdateReadyLottery(bean);
		
		LotteryBean lotteryParamBean = new LotteryBean();
		lotteryParamBean.setUserId(bean.getUserId());
		lotteryParamBean.setAwardId(bean.getAwardId());
		this.lotteryMapper.updateLotteryCount(lotteryParamBean);
	}
	
	/**
	 * 移除道具
	 * @param bean
	 */
	@Transactional
	public void dropReadyLottery(ReadyLotteryBean bean)
	{
		dropReadyLotteryB(bean);
	}

	private void dropReadyLotteryB(ReadyLotteryBean bean)
	{
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		int readyLotteryCount = this.lotteryMapper.countReadyLottery(bean);
		
		this.lotteryMapper.delReadyLottery(bean);
				
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(bean.getUserId());
		lotteryBean.setAwardId(bean.getAwardId());
		lotteryBean.setAddedCount(readyLotteryCount);
		this.lotteryMapper.increLotteryCount(lotteryBean);
	}
	
	public List<ReadyLotteryResultBean> findTomorrowReadyLottery(ReadyLotteryBean bean)
	{
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		List<ReadyLotteryBean> tempList = this.lotteryMapper.queryReadyLotteryAwardId(bean);
		
		List<ReadyLotteryResultBean> resultList = new ArrayList<>();
		for (ReadyLotteryBean tempBean : tempList)
		{
			ReadyLotteryResultBean resultBean = new ReadyLotteryResultBean();
			int tempAwardId = tempBean.getAwardId();
			resultBean.setAwardId(String.valueOf(tempBean.getAwardId()));
			resultBean.setAwardCount(String.valueOf(tempBean.getAwardCount()));
			
			LotteryBean lotteryParamBean = new LotteryBean();
			lotteryParamBean.setAwardId(tempAwardId);
			lotteryParamBean.setUserId(bean.getUserId());
			Integer tempTotalCount = this.lotteryMapper.countLottery(lotteryParamBean);
			if (null == tempTotalCount)
			{
				tempTotalCount = 0;
			}
			
			resultBean.setAwardTotalCount(String.valueOf(tempTotalCount));
			
			resultList.add(resultBean);
		}
		
		return resultList;
	}
	
	@Transactional
	public List<ReadyLotteryResultBean> increReadyLottery(ReadyLotteryBean bean)
	{
		LotteryBean lotteryParamBean = new LotteryBean();
		lotteryParamBean.setUserId(bean.getUserId());
		lotteryParamBean.setAwardId(bean.getAwardId());
		lotteryParamBean.setDaystamp(getTomorrowDateUndelimiterStr());
		this.lotteryMapper.increReadyLotteryCount(lotteryParamBean);
		
		this.lotteryMapper.delLotteryCount(lotteryParamBean);
		
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		List<ReadyLotteryResultBean> resultList = this.findTomorrowReadyLottery(bean);
		return resultList;
	}
	
	@Transactional
	public List<ReadyLotteryResultBean> decreReadyLottery(ReadyLotteryBean bean)
	{
		LotteryBean lotteryParamBean = new LotteryBean();
		lotteryParamBean.setUserId(bean.getUserId());
		lotteryParamBean.setAwardId(bean.getAwardId());
		lotteryParamBean.setDaystamp(getTomorrowDateUndelimiterStr());
		this.lotteryMapper.decreReadyLotteryCount(lotteryParamBean);
		
		this.lotteryMapper.addLotteryCount(lotteryParamBean);
		
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		List<ReadyLotteryResultBean> resultList = this.findTomorrowReadyLottery(bean);
		return resultList;
	}
	
	/**
	 * 替换道具
	 * @param bean
	 */
	@Transactional
	public List<ReadyLotteryResultBean> switchLottery(SwitchLotteryParamBean bean)
	{
		ReadyLotteryBean onBean = new ReadyLotteryBean();
		onBean.setUserId(bean.getUserId());
		onBean.setAwardId(bean.getOnAwardId());
		getReadyLotteryB(onBean);
		
		ReadyLotteryBean offBean = new ReadyLotteryBean();
		offBean.setUserId(bean.getUserId());
		offBean.setAwardId(bean.getOffAwardId());
		dropReadyLotteryB(offBean);
		
		ReadyLotteryBean tomorrowParamBean = new ReadyLotteryBean();
		tomorrowParamBean.setUserId(bean.getUserId());
		List<ReadyLotteryResultBean> resultList = findTomorrowReadyLottery(tomorrowParamBean);
		return resultList;
	}
	
	public String getTodayDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		
		String todayDateStr = DateFormatUtils.format(curDate, "yyyyMMdd");
		LOGGER.info(todayDateStr);
		return todayDateStr;
	}
	
	public String getTomorrowDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date tomorrowDate = DateUtils.addDays(curDate, 1);
		
		String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyyyMMdd");
		LOGGER.info(tomorrowDateStr);
		return tomorrowDateStr;
	}
	
	public String getTomorrowDateStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date tomorrowDate = DateUtils.addDays(curDate, 1);
		
		String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyyy-MM-dd");
		LOGGER.info(tomorrowDateStr);
		return tomorrowDateStr;
	}
	
	@Transactional
	public void syncTomorrowRole()
	{
		List<UserFamilyRelationBean> tempList = this.gameMapper.queryUserFamilyRelation();
		//格式：yyyyMMdd
		String tomorrowDayStrUndelimiter = getTomorrowDateUndelimiterStr();
		String tomorrowDateStr = getTomorrowDateStr();
		String startTime = tomorrowDateStr + " 00:00:00";
		String endTime = tomorrowDateStr + " 23:59:59";
		for (UserFamilyRelationBean tempBean : tempList)
		{
			UserFamilyRoleLogParamBean tempParamBean = new UserFamilyRoleLogParamBean();
			AdBeanUtils.copyNotNullProperties(tempParamBean, tempBean);;
			tempParamBean.setDaystamp(tomorrowDayStrUndelimiter);
			tempParamBean.setStartTime(startTime);
			tempParamBean.setEndTime(endTime);
			int roleCount = this.gameMapper.countTomorrowRole(tempParamBean);
			if (0 == roleCount)
			{
				this.gameMapper.saveUserFamilyRole(tempParamBean);
				long userFamilyRoleLogId = tempParamBean.getId();
				
				UserScoreDayParamBean tempScoreDayParamBean = new UserScoreDayParamBean();
				tempScoreDayParamBean.setUserId(tempBean.getUserId());
				tempScoreDayParamBean.setUserFamilyScoreId(userFamilyRoleLogId);
				tempScoreDayParamBean.setDaystamp(tomorrowDateStr);
				
				this.gameMapper.saveUserScoreDay(tempScoreDayParamBean);
			}
		}
	}
	
	/**
	 * 初始化第二天的家族PK关系
	 */
	@Transactional
	public void initTodayFamilyRelation()
	{
		FamilyChallengeLogParamBean bean = new FamilyChallengeLogParamBean();
		bean.setChallengeDay(getCurrentDayStr());
		List<FamilyChallengeLogBean> tempList = this.gameMapper.queryChallengeLog(bean);
		
		FamilyParamBean bronzeParam = new FamilyParamBean();
		bronzeParam.setFamilyType(FamilyConstant.BRONZE_TYPE);
		List<FamilyInfoBean> bronzeList = this.familyMapper.queryFamilyByType(bronzeParam);
		
		FamilyParamBean silverParam = new FamilyParamBean();
		silverParam.setFamilyType(FamilyConstant.SILVER_TYPE);
		List<FamilyInfoBean> silverList = this.familyMapper.queryFamilyByType(silverParam);
		
		FamilyParamBean goldParam = new FamilyParamBean();
		goldParam.setFamilyType(FamilyConstant.GOLD_TYPE);
		List<FamilyInfoBean> goldList = this.familyMapper.queryFamilyByType(goldParam);
		
		//tempList中的家族全部都能匹配上挑战的家族
		for (FamilyChallengeLogBean tempBean : tempList)
		{
			long familyId = tempBean.getFamilyId();
			int prevType = tempBean.getPrevType();
			int challengeType = tempBean.getChallengeType();

			if (challengeType == FamilyConstant.BRONZE_TYPE && CollectionUtils.isNotEmpty(bronzeList))
			{
				//绑定并从***List移除被配对的家族
				bindFamily(bronzeList, familyId);
				
				//移除familyId对应的家族
				getRidSelfFamily(bronzeList, silverList, goldList, familyId, prevType);
			}
			else if (challengeType == FamilyConstant.SILVER_TYPE && CollectionUtils.isNotEmpty(silverList))
			{
				bindFamily(silverList, familyId);
				
				getRidSelfFamily(bronzeList, silverList, goldList, familyId, prevType);
			}
			else if (challengeType == FamilyConstant.GOLD_TYPE && CollectionUtils.isNotEmpty(goldList))
			{
				bindFamily(goldList, familyId);
				
				getRidSelfFamily(bronzeList, silverList, goldList, familyId, prevType);
			}
		}
		
		List<FamilyInfoBean> leftFamilyList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(bronzeList))
		{
			leftFamilyList.addAll(bronzeList);
		}
		if (CollectionUtils.isNotEmpty(silverList))
		{
			leftFamilyList.addAll(silverList);
		}
		if (CollectionUtils.isNotEmpty(goldList))
		{
			leftFamilyList.addAll(goldList);
		}
		
		if (CollectionUtils.isNotEmpty(leftFamilyList))
		{
			if (leftFamilyList.size() == 1)
			{
				//temp settings robot
				FamilyInfoBean lastFamilyInfo = leftFamilyList.get(0);
				
				FamilyRelationParamBean relationParamBean = new FamilyRelationParamBean();
				relationParamBean.setSelfFamilyId(lastFamilyInfo.getId());
				relationParamBean.setCompetitorFamilyId(FamilyConstant.ROBOT_FAMILY_ID);
				relationParamBean.setDaystamp(getTomorrowDateStr());
				this.gameMapper.saveFamilyRelation(relationParamBean);
			}
			else
			{
				for (int i = 0; i < leftFamilyList.size(); i++)
				{
					if (i%2 == 1)
					{
						FamilyInfoBean firstFamily = leftFamilyList.get(i-1);
						FamilyInfoBean secondFamily = leftFamilyList.get(i);
						long firstFamilyId = firstFamily.getId();
						long secondFamilyId = secondFamily.getId();
						FamilyRelationParamBean relationParamBean = new FamilyRelationParamBean();
						relationParamBean.setSelfFamilyId(firstFamilyId);
						relationParamBean.setCompetitorFamilyId(secondFamilyId);
						relationParamBean.setDaystamp(getTomorrowDateStr());
						this.gameMapper.saveFamilyRelation(relationParamBean);
					}
				}
				
				if (leftFamilyList.size() % 2 == 1)
				{
					FamilyInfoBean lastFamilyInfo = leftFamilyList.get(leftFamilyList.size() - 1);
					//temp settings robot
					FamilyRelationParamBean relationParamBean = new FamilyRelationParamBean();
					relationParamBean.setSelfFamilyId(lastFamilyInfo.getId());
					relationParamBean.setCompetitorFamilyId(FamilyConstant.ROBOT_FAMILY_ID);
					relationParamBean.setDaystamp(getTomorrowDateStr());
					this.gameMapper.saveFamilyRelation(relationParamBean);
				}
			}
		}
	}

	/**
	 * 随机familyList中的家族与familyId配对，并从familyList移除被配对的家族
	 * @param familyList
	 * @param familyId
	 */
	private void bindFamily(List<FamilyInfoBean> familyList, long familyId)
	{
		int size = familyList.size();
		
		while (true)
		{
			int idx = RandUtils.generateRand(0, size-1);
			FamilyInfoBean familyInfoBean = familyList.get(idx);
			long competitorFamilyId = familyInfoBean.getId();
			
			if (familyId == competitorFamilyId)
			{
				continue;
			}
			
			FamilyRelationParamBean relationParamBean = new FamilyRelationParamBean();
			relationParamBean.setSelfFamilyId(familyId);
			relationParamBean.setCompetitorFamilyId(competitorFamilyId);
			relationParamBean.setDaystamp(getTomorrowDateStr());
			this.gameMapper.saveFamilyRelation(relationParamBean);
			
			familyList.remove(idx);
			break;
		}
	}
	
	//从前三个list中移除familyId对应的家族
	private void getRidSelfFamily(List<FamilyInfoBean> bronzeList, List<FamilyInfoBean> silverList, List<FamilyInfoBean> goldList, long familyId, int prevType)
	{
		if (prevType == FamilyConstant.BRONZE_TYPE)
		{
			Iterator<FamilyInfoBean> iter = bronzeList.iterator();
			while (iter.hasNext())
			{
				FamilyInfoBean tempFamilyInfo = iter.next();
				if (tempFamilyInfo.getId() == familyId)
				{
					iter.remove();
					break;
				}
			}
		}
		else if (prevType == FamilyConstant.SILVER_TYPE)
		{
			Iterator<FamilyInfoBean> iter = silverList.iterator();
			while (iter.hasNext())
			{
				FamilyInfoBean tempFamilyInfo = iter.next();
				if (tempFamilyInfo.getId() == familyId)
				{
					iter.remove();
					break;
				}
			}
		}
		else
		{
			Iterator<FamilyInfoBean> iter = goldList.iterator();
			while (iter.hasNext())
			{
				FamilyInfoBean tempFamilyInfo = iter.next();
				if (tempFamilyInfo.getId() == familyId)
				{
					iter.remove();
					break;
				}
			}
		}
	}
	
	public List<RoleCountResultBean> queryRolePercent(UserFamilyRoleLogParamBean bean)
	{
		List<RoleCountBean> tempList = this.gameMapper.countRoleByRole(bean);
		BigDecimal totalRoleCount = BigDecimal.valueOf(0);
		for (RoleCountBean tempBean : tempList)
		{
			int roleNum = tempBean.getRoleNum();
			totalRoleCount = totalRoleCount.add(BigDecimal.valueOf(roleNum));
		}
		
		List<RoleCountResultBean> resultList = new ArrayList<>();
		for (RoleCountBean tempBean : tempList)
		{
			int roleNum = tempBean.getRoleNum();
			BigDecimal percentBd = BigDecimal.valueOf(roleNum).divide(totalRoleCount, 2, BigDecimal.ROUND_HALF_UP);
			String percent = NumUtils.formattedDecimalToPercentage(percentBd.doubleValue());
			int role = tempBean.getRole();
			
			RoleCountResultBean tempResultBean = new RoleCountResultBean();
			tempResultBean.setPercent(percent);
			tempResultBean.setRole(String.valueOf(role));
			resultList.add(tempResultBean);
		}
		
		return resultList;
	}
}
