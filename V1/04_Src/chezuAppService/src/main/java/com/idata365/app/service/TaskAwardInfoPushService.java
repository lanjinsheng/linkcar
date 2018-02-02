package com.idata365.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AwardBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.TaskAwardInfoPush;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.mapper.AwardMapper;
import com.idata365.app.mapper.TaskAwardInfoPushMapper;

@Service
public class TaskAwardInfoPushService {
	@Autowired
	TaskAwardInfoPushMapper taskAwardInfoPushMapper;
	@Autowired
	MessageService messageService;
	@Autowired
   AwardMapper awardMapper;
	@Transactional
	public void doSomeThing() {
	 
	}
	 
	@Transactional
	public boolean pushMessage(TaskAwardInfoPush task) {
		AwardBean awardBean=awardMapper.queryAwardInfoById(task.getAwardInfoId());
		messageService.pushAwardMessageByTask(task.getAwardInfoId(),awardBean.getTaskTitle());
		return true;
	}
	
	public void initTask(AwardBean bean) {
		taskAwardInfoPushMapper.initTaskAwardInfoPush(bean);
	}
	
	
	public List<AwardBean> getNoSendMessageAwards() {
		return taskAwardInfoPushMapper.getNoSendMessageAwards();
	}
	public void updateAwardInfoSendType(Long id) {
		taskAwardInfoPushMapper.updateAwardInfoSendType(id);
	}
	
	public List<TaskAwardInfoPush> getAwardInfoPushTask(TaskAwardInfoPush task){
		//先锁定任务
		taskAwardInfoPushMapper.lockAwardInfoPushTask(task);
		//返回任务列表
		return taskAwardInfoPushMapper.getAwardInfoPushTask(task);
	}
	
	public	void updateSuccAwardInfoPushTask(TaskAwardInfoPush task) {
		taskAwardInfoPushMapper.updateAwardInfoPushSuccTask(task);
	}
//	
	public void updateFailAwardInfoPushTask(TaskAwardInfoPush task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskAwardInfoPushMapper.updateAwardInfoPushFailTask(task);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskAwardInfoPushMapper.clearLockTask(compareTimes);
	}
	
}
