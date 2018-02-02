package com.idata365.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.Message;
import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.mapper.TaskAchieveAddValueMapper;
import com.idata365.app.mapper.TaskMessagePushMapper;
import com.idata365.app.service.common.AchieveCommService;

@Service
public class TaskMessagePushService {
	@Autowired
     TaskMessagePushMapper taskMessagePushMapper;
	@Autowired
	MessageService messageService;
	@Transactional
	public void doSomeThing() {
	 
	}
	 
	@Transactional
	public boolean pushMessage(TaskMessagePush task) {
		Message msg=messageService.getMessageById(task.getMessageId());
		messageService.pushMessageByTask(msg);
		return true;
	}
	
 
	public List<TaskMessagePush> getMessagePushTask(TaskMessagePush task){
		//先锁定任务
		taskMessagePushMapper.lockMessagePushTask(task);
		//返回任务列表
		return taskMessagePushMapper.getMessagePushTask(task);
	}
	
	public	void updateSuccMessagePushTask(TaskMessagePush task) {
		taskMessagePushMapper.updateMessagePushSuccTask(task);
	}
//	
	public void updateFailMessagePushTask(TaskMessagePush task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskMessagePushMapper.updateMessagePushFailTask(task);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskMessagePushMapper.clearLockTask(compareTimes);
	}
	
}
