package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.UserRoleLog;
import com.idata365.mapper.app.UserRoleLogMapper;

@Service
public class UserRoleLogService extends BaseService<UserRoleLogService>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleLogService.class);
	
	@Autowired
	private UserRoleLogMapper userRoleLogMapper;
	
	@Transactional
	public UserRoleLog getLatestUserRoleLog(Long userId)
	{
		UserRoleLog userRoleLog=new UserRoleLog();
		userRoleLog.setUserId(userId);
		userRoleLog=userRoleLogMapper.getLatestUserRoleLog(userRoleLog);
		return userRoleLog;
	}
	public UserRoleLog getLatestUserRoleLogNoTrans(Long userId)
	{
		UserRoleLog userRoleLog=new UserRoleLog();
		userRoleLog.setUserId(userId);
		userRoleLog=userRoleLogMapper.getLatestUserRoleLog(userRoleLog);
		return userRoleLog;
	}
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		if(diff!=0) {
			Date newDate = DateUtils.addDays(curDate, diff);
			String newDateStr = DateFormatUtils.format(newDate, "yyyy-MM-dd HH:mm:ss");
			return newDateStr;
		}else {
			String newDateStr = DateFormatUtils.format(curDate, "yyyy-MM-dd HH:mm:ss");
			return newDateStr;
		}
		 
	}
 
	public String getDateStr2(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		if(diff!=0) {
			Date newDate = DateUtils.addDays(curDate, diff);
			String newDateStr = DateFormatUtils.format(newDate, "yyyyMMdd");
			return newDateStr;
		}else {
			String newDateStr = DateFormatUtils.format(curDate, "yyyyMMdd");
			return newDateStr;
		}
		 
	}
}
