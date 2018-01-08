package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.UserScoreDayStat;

public interface  UserScoreDayStatMapper {
	int updateUserScoreDayStat(UserScoreDayStat userScoreDayStat);
	int insertOrUpdateUserDayStat(UserScoreDayStat userScoreDayStat);
	
	
	void lockUserScoreDayTask(UserScoreDayStat userScoreDayStat);
	
	List<UserScoreDayStat> getUserScoreDayTask(UserScoreDayStat userScoreDayStat);
	
	void updateUserScoreDaySuccTask(UserScoreDayStat userScoreDayStat);
	
	void updateUserScoreDayFailTask(UserScoreDayStat userScoreDayStat);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
