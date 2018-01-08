package com.idata365.app.service;

import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.UserActiveLogs;
import com.idata365.app.mapper.UserActiveLogsMapper;

@Service
public class BusinessDatasService extends BaseService<BusinessDatasService>
{
	private static final Logger LOG = LoggerFactory.getLogger(BusinessDatasService.class);
	
	 @Autowired
	UserActiveLogsMapper userActiveLogsMapper;
	public void insertActiveLogs(long userId)
	{
		UserActiveLogs log=new UserActiveLogs();
		log.setUserId(userId);
		String dayStr=getCurrentDayStr();
		log.setDaystamp(dayStr);
		userActiveLogsMapper.insertUserActiveLogs(log);
		
	}
	
	
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN);
		return dayStr;
	}
}
