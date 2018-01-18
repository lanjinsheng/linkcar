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
import com.idata365.app.entity.FamilyChallengeLogBean;
import com.idata365.app.entity.FamilyChallengeLogParamBean;
import com.idata365.app.entity.FamilyInfoBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.ParkStationParamBean;
import com.idata365.app.entity.UserFamilyRelationBean;
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
		
		this.taskMapper.delStations(delParamList);
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
	
	@Transactional
	public void syncTomorrowRole()
	{

		List<UserFamilyRelationBean> tempList = this.taskMapper.queryUserFamilyRelation();
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
			int roleCount = this.taskMapper.countTomorrowRole(tempParamBean);
			if (0 == roleCount)
			{
				this.taskMapper.saveUserFamilyRole(tempParamBean);
				long userFamilyRoleLogId = tempParamBean.getId();
				
				UserScoreDayParamBean tempScoreDayParamBean = new UserScoreDayParamBean();
				tempScoreDayParamBean.setUserId(tempBean.getUserId());
				tempScoreDayParamBean.setUserFamilyScoreId(userFamilyRoleLogId);
				tempScoreDayParamBean.setDaystamp(tomorrowDateStr);
				
				this.taskMapper.saveUserScoreDay(tempScoreDayParamBean);
			}
		}
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
	public void initTodayFamilyRelation()
	{
		FamilyChallengeLogParamBean bean = new FamilyChallengeLogParamBean();
		bean.setChallengeDay(getCurrentDayStr());
		List<FamilyChallengeLogBean> tempList = this.taskMapper.queryChallengeLog(bean);
		
		FamilyParamBean bronzeParam = new FamilyParamBean();
		bronzeParam.setFamilyType(FamilyConstant.BRONZE_TYPE);
		List<FamilyInfoBean> bronzeList = this.taskMapper.queryFamilyByType(bronzeParam);
		
		FamilyParamBean silverParam = new FamilyParamBean();
		silverParam.setFamilyType(FamilyConstant.SILVER_TYPE);
		List<FamilyInfoBean> silverList = this.taskMapper.queryFamilyByType(silverParam);
		
		FamilyParamBean goldParam = new FamilyParamBean();
		goldParam.setFamilyType(FamilyConstant.GOLD_TYPE);
		List<FamilyInfoBean> goldList = this.taskMapper.queryFamilyByType(goldParam);
		
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
				this.taskMapper.saveFamilyRelation(relationParamBean);
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
						this.taskMapper.saveFamilyRelation(relationParamBean);
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
					this.taskMapper.saveFamilyRelation(relationParamBean);
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
			this.taskMapper.saveFamilyRelation(relationParamBean);
			
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
}
