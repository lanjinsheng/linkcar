package com.idata365.app.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.TaskAchieveAddValue;
import com.idata365.app.mapper.TaskAchieveAddValueMapper;
import com.idata365.app.service.common.AchieveCommService;

@Service
public class TaskAchieveAddValueService {
	@Autowired
     TaskAchieveAddValueMapper taskAchieveAddValueMapper;
	@Autowired
	AchieveCommService achieveCommService;
	
	@Transactional
	public void doSomeThing() {
	 
	}
	 
	@Transactional
	public boolean calAchieve(TaskAchieveAddValue task) {
	 achieveCommService.dealTaskAchieveAddValue(task);
	 return true;
	}
	
 
	public List<TaskAchieveAddValue> getAchieveAddValueTask(TaskAchieveAddValue task){
		//先锁定任务
		taskAchieveAddValueMapper.lockAchieveAddValueTask(task);
		//返回任务列表
		return taskAchieveAddValueMapper.getAchieveAddValueTask(task);
	}
	
	public	void updateSuccAchieveAddValueTask(TaskAchieveAddValue task) {
		taskAchieveAddValueMapper.updateAchieveAddValueSuccTask(task);
	}
//	
	public void updateFailAchieveAddValueTask(TaskAchieveAddValue task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskAchieveAddValueMapper.updateAchieveAddValueFailTask(task);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskAchieveAddValueMapper.clearLockTask(compareTimes);
	}
	
}
