package com.idata365.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.TaskImMessagePush;
import com.idata365.app.mapper.TaskImMessagePushMapper;

@Service
public class TaskImMessagePushService {
	@Autowired
    TaskImMessagePushMapper taskImMessagePushMapper;
	@Autowired
	MessageService messageService;
	
	@Autowired
	ImService imService;
	@Transactional
	public int insertTaskImMessagePush(TaskImMessagePush task) {
		return taskImMessagePushMapper.insertTaskImMessagePush(task);
	}
	
	@Transactional
	public boolean pushMessage(TaskImMessagePush task) {
//		ImMsg msg=imService.getMsgById(task.getImMessageId());
//		messageService.pushImMessageByTask(msg);
		return true;
	}
	
 
	public List<TaskImMessagePush> getImMessagePushTask(TaskImMessagePush task){
		//先锁定任务
		taskImMessagePushMapper.lockImMessagePushTask(task);
		//返回任务列表
		return taskImMessagePushMapper.getImMessagePushTask(task);
	}
	
	public	void updateSuccImMessagePushTask(TaskImMessagePush task) {
		taskImMessagePushMapper.updateImMessagePushSuccTask(task);
	}
//	
	public void updateFailImMessagePushTask(TaskImMessagePush task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskImMessagePushMapper.updateImMessagePushFailTask(task);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskImMessagePushMapper.clearLockTask(compareTimes);
	}
	
}
