package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.TaskAwardInfoPush;

public interface TaskAwardInfoPushMapper {
	public int initTaskAwardInfoPush( AwardBean awardBean);
	 List<AwardBean> getNoSendMessageAwards();
	 
     int  updateAwardInfoSendType(@Param("id") Long id);
	 
	void lockAwardInfoPushTask(TaskAwardInfoPush task);
	
	List<TaskAwardInfoPush> getAwardInfoPushTask(TaskAwardInfoPush task);
	
	void updateAwardInfoPushSuccTask(TaskAwardInfoPush task);
	
	void updateAwardInfoPushFailTask(TaskAwardInfoPush task);
	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
}
