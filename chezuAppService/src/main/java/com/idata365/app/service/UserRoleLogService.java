package com.idata365.app.service;

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

import com.idata365.app.entity.UserRoleLog;
import com.idata365.app.mapper.UserRoleLogMapper;
import com.idata365.app.util.DateTools;

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
	
	public UserRoleLog getYestodayUserRoleLogNoTrans(Long userId)
	{
		UserRoleLog userRoleLog=new UserRoleLog();
		userRoleLog.setUserId(userId);
		
		userRoleLog.setDaystamp(getDateStr2(-1));
		userRoleLog=userRoleLogMapper.getYestodayUserRoleLog(userRoleLog);
		if(userRoleLog==null) {
			userRoleLog=userRoleLogMapper.getLatestUserRoleLogByUserId(userId);
		}
		return userRoleLog;
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
	@Transactional
	public UserRoleLog insertUserRoleLog(Long userId,int role)
	{
		//先更新之前的endTime
		String now=getDateStr(0);
		UserRoleLog urole= getLatestUserRoleLog(userId);
		if(urole!=null) {
			urole.setEndTime(now);
			userRoleLogMapper.updateUserRoleEndTime(urole);
		}
		UserRoleLog userRoleLog=new UserRoleLog();
		userRoleLog.setUserId(userId);
		userRoleLog.setRole(role);
		userRoleLog.setDaystamp(DateTools.getYYYYMMDD());
		userRoleLog.setStartTime(getDateStr(0));
		userRoleLog.setEndTime(getDateStr(3650));
		userRoleLogMapper.insertUserRole(userRoleLog);
		//更新家族里的role信息
		userRoleLogMapper.updateUserFamilyRelationRole(userRoleLog);
		return userRoleLog;
	}
 
}
