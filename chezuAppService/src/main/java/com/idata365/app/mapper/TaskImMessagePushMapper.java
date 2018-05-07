package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.TaskImMessagePush;

public interface TaskImMessagePushMapper {
	public int insertTaskImMessagePush(TaskImMessagePush task);
	
	void lockImMessagePushTask(TaskImMessagePush task);
	
	List<TaskImMessagePush> getImMessagePushTask(TaskImMessagePush task);
	
	void updateImMessagePushSuccTask(TaskImMessagePush task);
	
	void updateImMessagePushFailTask(TaskImMessagePush task);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
