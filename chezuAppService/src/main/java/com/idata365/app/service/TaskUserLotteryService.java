package com.idata365.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.Message;
import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.entity.TaskMessagePush;
import com.idata365.app.entity.TaskUserLottery;
import com.idata365.app.mapper.TaskAchieveAddValueMapper;
import com.idata365.app.mapper.TaskMessagePushMapper;
import com.idata365.app.mapper.TaskUserLotteryMapper;
import com.idata365.app.service.common.AchieveCommService;

@Service
public class TaskUserLotteryService {
	@Autowired
     TaskUserLotteryMapper taskUserLotteryMapper;
 
	@Transactional
	public void doSomeThing() {
	 
	}
	 
	public boolean calUserTotteryTask(TaskUserLottery task) {
		//待小明哥补充
		return true;
	}
	
 
	public List<TaskUserLottery> geUserLotteryTask(TaskUserLottery task){
		//先锁定任务
		taskUserLotteryMapper.lockUserLotteryTask(task);
		//返回任务列表
		return taskUserLotteryMapper.getUserLotteryTask(task);
	}
	
	public	void updateSuccUserLotteryTask(TaskUserLottery task) {
		taskUserLotteryMapper.updateUserLotterySuccTask(task);
	}
//	
	public void updateFailUserLotteryTask(TaskUserLottery task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskUserLotteryMapper.updateUserLotteryFailTask(task);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskUserLotteryMapper.clearLockTask(compareTimes);
	}
	
}
