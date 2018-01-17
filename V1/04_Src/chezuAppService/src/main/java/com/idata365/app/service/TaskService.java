package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.ParkStationParamBean;
import com.idata365.app.mapper.TaskMapper;

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
}
