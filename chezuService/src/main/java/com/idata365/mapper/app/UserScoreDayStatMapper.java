package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserScoreDayStat;

public interface  UserScoreDayStatMapper {
//	int updateUserScoreDayStat(UserScoreDayStat userScoreDayStat);//更新驾驶行为
	int updateUserScoreDayById(UserScoreDayStat userScoreDayStat);//更新驾驶得分
	
	int insertOrUpdateUserDayStat(UserScoreDayStat userScoreDayStat);
	
	 UserScoreDayStat getUserDayScoreByUserFamily(UserFamilyRoleLog userFamilyRoleLog);
	
	
	void lockUserDayScoreTask(UserScoreDayStat userScoreDayStat);
	
	List<UserScoreDayStat> getUserDayScoreTask(UserScoreDayStat userScoreDayStat);
	
	void updateUserDayScoreSuccTask(UserScoreDayStat userScoreDayStat);
	
	void updateUserDayScoreFailTask(UserScoreDayStat userScoreDayStat);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
