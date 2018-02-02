package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.TaskMessagePush;

public interface TaskMessagePushMapper {
	public int insertTaskMessagePush(TaskMessagePush task);
	
	void lockMessagePushTask(TaskMessagePush task);
	
	List<TaskMessagePush> getMessagePushTask(TaskMessagePush task);
	
	void updateMessagePushSuccTask(TaskMessagePush task);
	
	void updateMessagePushFailTask(TaskMessagePush task);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
