package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.TaskPowerLogs;


public interface TaskPowerLogsMapper{
	public int insertTaskPowerLogs(TaskPowerLogs task);
	public TaskPowerLogs getPowerLog(TaskPowerLogs task);
	
	void lockPowerLogsTask(TaskPowerLogs task);
	
	List<TaskPowerLogs> getPowerLogsTask(TaskPowerLogs task);
	
	void updatePowerLogsSuccTask(TaskPowerLogs task);
	
	void updatePowerLogsFailTask(TaskPowerLogs task);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
