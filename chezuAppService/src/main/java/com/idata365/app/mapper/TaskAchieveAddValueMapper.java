package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.TaskAchieveAddValue;

public interface TaskAchieveAddValueMapper {
	public int insertTaskAchieveAddValue(TaskAchieveAddValue taskAchieveAddValue);
	
	void lockAchieveAddValueTask(TaskAchieveAddValue task);
	
	List<TaskAchieveAddValue> getAchieveAddValueTask(TaskAchieveAddValue task);
	
	void updateAchieveAddValueSuccTask(TaskAchieveAddValue task);
	
	void updateAchieveAddValueFailTask(TaskAchieveAddValue task);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
