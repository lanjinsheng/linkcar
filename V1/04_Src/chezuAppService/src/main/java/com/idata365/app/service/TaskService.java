package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.FamilyConstant;
import com.idata365.app.constant.LotteryConstant;
import com.idata365.app.constant.RoleConstant;
import com.idata365.app.entity.DriverVehicleResultBean;
import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.ParkStationParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserScoreDayParamBean;
import com.idata365.app.mapper.TaskMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.RandUtils;

@Service
public class TaskService extends BaseService<TaskService>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private TaskMapper taskMapper;
	@Transactional
	public void updateUserScoreDayByUserId(Long userId){
		taskMapper.updateUserScoreDayByUserId(userId);
	}
	@Transactional
	public void resetStations()
	{
		FamilyRelationBean paramRelationBean = new FamilyRelationBean();
		paramRelationBean.setDaystamp(getCurrentDayStr());
		List<FamilyRelationBean> relationList = this.taskMapper.queryFamilyRelations(paramRelationBean);
		
		List<Long> delParamList = new ArrayList<>();
		List<ParkStationParamBean> stationParamList = new ArrayList<>();
		for (FamilyRelationBean tempBean : relationList)
		{
			long tempFamilyReationId = tempBean.getFamilyRelationId();
			delParamList.add(tempFamilyReationId);
			
			FamilyRelationBean countParamBean = new FamilyRelationBean();
			countParamBean.setFamilyId(tempBean.getFamilyId1());
			int count1 = this.taskMapper.countByFamily(countParamBean);
			
			countParamBean.setFamilyId(tempBean.getFamilyId2());
			int count2 = this.taskMapper.countByFamily(countParamBean);
			
			int memberCount = count1 + count2;
			
			int stationCount;
			if (memberCount <= 2)
			{
				stationCount = 1;
			}
			else
			{
				stationCount = memberCount -2;
			}
			for (int i = 0; i < stationCount; i++)
			{
				ParkStationParamBean stationParamBean = new ParkStationParamBean();
				stationParamBean.setFamilyRelationId(tempFamilyReationId);
				stationParamBean.setStatus("NO_PEOPLE");
				stationParamBean.setUpdateTime(getCurrentTs());
				stationParamList.add(stationParamBean);
			}
		}
		if(delParamList.size()>0){
			this.taskMapper.delStations(delParamList);
		}
		this.taskMapper.saveStations(stationParamList);
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
	
	private String getCurrentTs()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.SECOND_FORMAT_PATTERN);
		return dayStr;
	}
	
	/**
	 * 初始化明天的userFamilyRoleLog
	 * 初始化userFamilyRelation中的role
	 * 初始化明天的userScoreDayStat
	 * 给每个用户赠送一张小纸条
	 * 
	 */
	@Transactional
	public void syncTomorrowRole()
	{
		//查询用户家族实时关系
		List<UserFamilyRelationBean> tempList = this.taskMapper.queryUserFamilyRelation();
		//格式：yyyyMMdd
		String tomorrowDayStrUndelimiter = getTomorrowDateUndelimiterStr();
		String tomorrowDateStr = getTomorrowDateStr();
		String startTime = tomorrowDateStr + " 00:00:00";
		String endTime = tomorrowDateStr + " 23:59:59";
		for (UserFamilyRelationBean tempBean : tempList)
		{
			UserFamilyRoleLogParamBean tempParamBean = new UserFamilyRoleLogParamBean();
			AdBeanUtils.copyNotNullProperties(tempParamBean, tempBean);
			tempParamBean.setDaystamp(tomorrowDayStrUndelimiter);
			tempParamBean.setStartTime(startTime);
			tempParamBean.setEndTime(endTime);
			
			//count用户明天的角色
			UserFamilyRoleLogBean tomorrowRoleLog = this.taskMapper.queryFamilyRoleLog(tempParamBean);
			if (null == tomorrowRoleLog)
			{
				//初始化明天的userFamilyRoleLog
				//如果角色已经被试用过一天，则明天角色恢复为默认角色
//				if (flag)
//				{
//					tempParamBean.setRole(RoleConstant.JIANBING_ROLE);
//				}
				
				this.taskMapper.saveUserFamilyRole(tempParamBean);
				long userFamilyRoleLogId = tempParamBean.getId();
				
				//初始化userFamilyRelation中的role
//				if (flag)
//				{
//					UserFamilyRoleLogParamBean userRoleParamBean = new UserFamilyRoleLogParamBean();
//					userRoleParamBean.setRole(RoleConstant.JIANBING_ROLE);
//					userRoleParamBean.setUserId(tempBean.getUserId());
//					userRoleParamBean.setFamilyId(tempBean.getFamilyId());
//					this.taskMapper.updateUserRole(userRoleParamBean);
//				}
				
				//初始化明天的userScoreDayStat
				UserScoreDayParamBean tempScoreDayParamBean = new UserScoreDayParamBean();
				tempScoreDayParamBean.setUserId(tempBean.getUserId());
				tempScoreDayParamBean.setUserFamilyScoreId(userFamilyRoleLogId);
				tempScoreDayParamBean.setDaystamp(tomorrowDateStr);
				tempScoreDayParamBean.setFamilyId(tempBean.getFamilyId());
				tempScoreDayParamBean.setAvgScore(0d);
				this.taskMapper.saveOrUpdateUserScoreDay(tempScoreDayParamBean);
			}
			else
			{
				//更新userFamilyRelation中的role
				UserFamilyRoleLogParamBean userRoleParamBean = new UserFamilyRoleLogParamBean();
				userRoleParamBean.setRole(tomorrowRoleLog.getRole());
				userRoleParamBean.setUserId(tempBean.getUserId());
				userRoleParamBean.setFamilyId(tempBean.getFamilyId());
				this.taskMapper.updateUserRole(userRoleParamBean);
				
				//初始化明天的userScoreDayStat
				UserScoreDayParamBean tempScoreDayParamBean = new UserScoreDayParamBean();
				tempScoreDayParamBean.setUserId(tempBean.getUserId());
				tempScoreDayParamBean.setUserFamilyScoreId(tomorrowRoleLog.getId());
				tempScoreDayParamBean.setDaystamp(tomorrowDateStr);
				tempScoreDayParamBean.setFamilyId(tempBean.getFamilyId());
				tempScoreDayParamBean.setAvgScore(0d);
				this.taskMapper.saveOrUpdateUserScoreDay(tempScoreDayParamBean);
			}
		}
		

	}
	
	/**
	 * 赠送道具接口
	 * @param tempUserId
	 */
	@Transactional
	public void givenLottery(Long tempUserId)
	{
		LOGGER.info("givenLottery======tempUserId======{}", tempUserId);
		
		//赠送小纸条
		LotteryBean tempLotteryParamBean = new LotteryBean();
		tempLotteryParamBean.setUserId(tempUserId);
		tempLotteryParamBean.setAwardId(LotteryConstant.ZHITIAO_LOTTERY);
		tempLotteryParamBean.setAwardCount(1);
		
		this.taskMapper.saveOrUpdate(tempLotteryParamBean);
		
		//煎饼侠赠送小马扎10张，前一天赠送的使期失效
		UserFamilyRoleLogBean countsRoleParam = new UserFamilyRoleLogBean();
		countsRoleParam.setUserId(tempUserId);
		countsRoleParam.setRole(RoleConstant.JIANBING_ROLE);
		int roleCounts = this.taskMapper.countsRole(countsRoleParam);
		if (roleCounts > 0)
		{
			LotteryBean countLotteryParam = new LotteryBean();
			countLotteryParam.setUserId(tempUserId);
			countLotteryParam.setAwardId(LotteryConstant.MAZHA_LOTTERY);
			Integer mazhaCount = this.taskMapper.countLottery(countLotteryParam);
			
			LotteryBean mazhaParam = new LotteryBean();
			mazhaParam.setUserId(tempUserId);
			mazhaParam.setAwardId(LotteryConstant.MAZHA_LOTTERY);
			mazhaParam.setAwardCount(10);
			if (null == mazhaCount)
			{
				this.taskMapper.saveOrUpdate(mazhaParam);
			}
			else
			{
				this.taskMapper.resetMaZhaCount(mazhaParam);
			}
		}
		else
		{
			//如果不是煎饼侠角色，清空小马扎道具
			LotteryBean mazhaParam = new LotteryBean();
			mazhaParam.setUserId(tempUserId);
			mazhaParam.setAwardId(LotteryConstant.MAZHA_LOTTERY);
			mazhaParam.setAwardCount(0);
			this.taskMapper.resetMaZhaCount(mazhaParam);
		}
		
		this.taskMapper.countsRole(countsRoleParam);
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
	
	/**
	 * 初始化第二天的家族PK关系
	 */
	@Transactional
	
	/**
	 * 随机familyList中的家族与familyId配对，并从familyList移除被配对的家族
	 * @param familyList
	 * @param familyId
	 */
	private void bindFamily(List<FamilyInfoBean> familyList, long familyId, String tomorrowDateStr)
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
			relationParamBean.setDaystamp(tomorrowDateStr);
			this.taskMapper.saveFamilyRelation(relationParamBean);
			
			familyList.remove(idx);
			break;
		}
	}
	
	
}
