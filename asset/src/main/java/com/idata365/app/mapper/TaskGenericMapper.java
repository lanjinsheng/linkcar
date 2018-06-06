package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.TaskGeneric;

public interface TaskGenericMapper {
	int insertTask(TaskGeneric task);

	TaskGeneric getByGenericKey(@Param("genericKey") String genericKey);

	void initUserDayRewardTask(Map<String, Object> map);

	long getPowerDayTotal(Map<String, Object> map);

	Map<String, Object> getPersonPower(Map<String, Object> map);

	Map<String, Object> getUserPowerByUserId(Map<String, Object> map);

	long getByFamilyTotal(@Param("season") String season);
	
	Map<String,Object> getFamilyTotalByST(Map<String, Object> map);
	
	void initDoFamilyDayReward(Map<String, Object> map);
	void initDoFamilySeasonReward(Map<String, Object> map);
	
	void lockTask(TaskGeneric task);

	List<TaskGeneric> getTask(TaskGeneric task);

	void updateSuccTask(TaskGeneric task);

	void updateFailTask(TaskGeneric task);

	void clearLockTask(@Param("compareTimes") Long compareTimes);

}
