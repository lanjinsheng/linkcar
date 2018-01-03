package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.idata365.app.constant.LotteryConstant;
import com.idata365.app.constant.ResultConstant;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.PenalResultBean;
import com.idata365.app.entity.ReadyLotteryBean;
import com.idata365.app.entity.StationBean;
import com.idata365.app.entity.StationResultBean;
import com.idata365.app.entity.TravelHistoryParamBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;
import com.idata365.app.entity.ViolationStatResultAllBean;
import com.idata365.app.entity.ViolationStatResultBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.GameMapper;
import com.idata365.app.mapper.LotteryMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.RandUtils;

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
	
	@Transactional
	public void challengeFamily(GameFamilyParamBean bean)
	{
		List<Long> familyIdList = this.gameMapper.queryIdleFamily(bean);
		int size = familyIdList.size();
		int idx = RandUtils.generateRand(0, size-1);
		Long selFamilyId = familyIdList.get(idx);
		
		long familyId = bean.getFamilyId();

		Date todayDate = Calendar.getInstance().getTime();
		String todayStr = DateFormatUtils.format(todayDate, DateConstant.DAY_PATTERN_DELIMIT);
		
		FamilyRelationParamBean bean1 = new FamilyRelationParamBean();
		bean1.setSelfFamilyId(familyId);
		bean1.setCompetitorFamilyId(selFamilyId);
		bean1.setDaystamp(todayStr);
		this.gameMapper.saveFamilyRelation(bean1);
		
/*		FamilyRelationParamBean bean2 = new FamilyRelationParamBean();
		bean2.setSelfFamilyId(selFamilyId);
		bean2.setCompetitorFamilyId(familyId);
		bean2.setDaystamp(todayStr);
		this.gameMapper.saveFamilyRelation(bean2);*/
	}
	
	public String judgeChallengeFlag(GameFamilyParamBean bean)
	{
		long familyId = bean.getFamilyId();
		FamilyRelationBean familyRelationBean = new FamilyRelationBean();
		familyRelationBean.setFamilyId(familyId);
		List<Long> familyIdList = this.familyMapper.queryFamilyRelationIds(familyRelationBean);
		
		if (CollectionUtils.isEmpty(familyIdList))
		{
			return "0";
		}
		else
		{
			return "1";
		}
	}
	
	@Transactional
	public PenalResultBean penalSpeed(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalBrake(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalTurn(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalOverspeed(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalNightDrive(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalTiredDrive(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
		
		return resultBean;
	}
	
	@Transactional
	public PenalResultBean penalIllegalStop(GameFamilyParamBean bean, long userId)
	{
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setUserId(userId);
		lotteryBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		
		int countLottery = this.lotteryMapper.countLottery(lotteryBean);
		
		PenalResultBean resultBean = new PenalResultBean();
		//纸条用光
		if (countLottery == 0)
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
	public int holdAtStation(GameFamilyParamBean bean)
	{
		int result = this.gameMapper.updateToHoldParkStation(bean);
		return result;
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
		bean.setDaystamp(getCurrentDayStrUnDelimiter());
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
	public void getReadyLottery(ReadyLotteryBean bean)
	{
		bean.setDaystamp(getTomorrowDateUndelimiterStr());
		this.lotteryMapper.saveOrUpdateReadyLottery(bean);
		
		LotteryBean lotteryParamBean = new LotteryBean();
		lotteryParamBean.setUserId(bean.getUserId());
		lotteryParamBean.setAwardId(bean.getAwardId());
		this.lotteryMapper.updateLotteryCount(lotteryParamBean);
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
		
		String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyyy-MM-dd");
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
}
