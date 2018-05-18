package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DriveScore;
import com.idata365.entity.UserScoreDayStat;
import com.idata365.mapper.app.DriveScoreMapper;
import com.idata365.mapper.app.FamilyInfoMapper;
import com.idata365.mapper.app.TaskAchieveAddValueMapper;
import com.idata365.mapper.app.UserFamilyLogsMapper;
import com.idata365.mapper.app.UserScoreDayStatMapper;


@Service
public class CalScoreUserDayServiceV2  extends BaseService<CalScoreUserDayServiceV2>{
	@Autowired
	UserScoreDayStatMapper userScoreDayStatMapper;
	@Autowired
	UserFamilyLogsMapper userFamilyScoreMapper;
	@Autowired
	DriveScoreMapper driveScoreMapper;
	@Autowired
	TaskAchieveAddValueMapper taskAchieveAddValueMapper;
	@Autowired
	FamilyInfoMapper familyInfoMapper;
	@Transactional
	public boolean calScoreUserDay(UserScoreDayStat userScoreDayStat) {
		return true;
	}
//	public boolean addAchieve(long keyId,Double value,AchieveEnum type) {
//		TaskAchieveAddValue taskAchieveAddValue=new TaskAchieveAddValue();
//		taskAchieveAddValue.setAchieveType(type);
//		taskAchieveAddValue.setKeyId(keyId);
//		taskAchieveAddValue.setAddValue(value);
//		taskAchieveAddValueMapper.insertTaskAchieveAddValue(taskAchieveAddValue);
//		return true;
//	}
 
	@Transactional
	public void updateUserDayScore(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserScoreDayById(userScoreDayStat);
	}
	@Transactional
	public List<UserScoreDayStat> getUserScoreDayTask(UserScoreDayStat userScoreDayStat){
		//先锁定任务
		userScoreDayStatMapper.lockUserDayScoreTask(userScoreDayStat);
		//返回任务列表
		return userScoreDayStatMapper.getUserDayScoreTask(userScoreDayStat);
	}
	@Transactional
	public	void updateSuccUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserDayScoreSuccTask(userScoreDayStat);
	}
//	
	@Transactional
	public void updateFailUserScoreDayTask(UserScoreDayStat userScoreDayStat) {
		userScoreDayStatMapper.updateUserDayScoreFailTask(userScoreDayStat);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		userScoreDayStatMapper.clearLockTask(compareTimes);
	}
//	
}
