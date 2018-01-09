package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.mapper.app.TaskFamilyDayScoreMapper;
import com.idata365.mapper.app.TaskFamilyOrderMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
import com.idata365.mapper.app.TaskSystemScoreFlagMapper;
@Service
public class ConfigSystemTaskService  extends BaseService<ConfigSystemTaskService>{
	private static final Logger LOG = LoggerFactory.getLogger(ConfigSystemTaskService.class);
	@Autowired
	TaskFamilyDayScoreMapper taskFamilyDayScoreMapper;
	@Autowired
    TaskSystemScoreFlagMapper taskSystemScoreFlagMapper;
	@Autowired
	TaskFamilyPkMapper taskFamilyPkMapper;
	
	@Autowired
	TaskFamilyOrderMapper taskFamilyOrderMapper;
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		LOG.info(dayStr);
		return dayStr;
	}
	public void configSystemTask(){
		String dayStamp=getDateStr(-1);
		TaskSystemScoreFlag taskSystemScoreFlag=new TaskSystemScoreFlag();
		taskSystemScoreFlag.setDaystamp(dayStamp);
		int insert=taskSystemScoreFlagMapper.insertSystemScoreFlag(taskSystemScoreFlag);
		
		List<TaskSystemScoreFlag> tasks=taskSystemScoreFlagMapper.getUnInitSystemScoreFlagList();
		for (TaskSystemScoreFlag task:tasks) {
			Map<String,Object> map=new HashMap<String,Object>();
			String day=task.getDaystamp().replace("-", "");
			map.put("startTime", day+"000000");
			map.put("endTime", day+"235959");
			map.put("daystamp", task.getDaystamp());
			taskFamilyDayScoreMapper.insertTaskFamilyDayScoreByTime(map);
			task.setTaskFamilyInit(1);
			taskSystemScoreFlagMapper.updateSystemScoreFlag(task);
		}
		
		List<TaskSystemScoreFlag> tasks2=taskSystemScoreFlagMapper.getUnInitPkFlagList();
		for (TaskSystemScoreFlag task:tasks2) {
			taskFamilyPkMapper.initTaskFamilyPk(task);
			task.setTaskFamilyPkInit(1);;
			taskSystemScoreFlagMapper.updatePkInit(task);
		}
		 
		
		List<TaskSystemScoreFlag> tasks3=taskSystemScoreFlagMapper.getUnInitOrderFlagList();
		for (TaskSystemScoreFlag task:tasks3) {
			taskFamilyOrderMapper.initTaskFamilyOrder(task);
			task.setTaskFamilyOrderInit(1);;
			taskSystemScoreFlagMapper.updateOrderInit(task);
		}
		 
	}
	public void finishConfigSystemUserScoreTask(TaskSystemScoreFlag task) {
		task.setUserDayScoreFlag(1);
		taskSystemScoreFlagMapper.finishUserDayScoreTask(task);
		
	}
	public List<TaskSystemScoreFlag> getUnFinishUserDayScore(){
		return taskSystemScoreFlagMapper.getUnFinishDayScoreList();
	}
	
	
	public List<TaskSystemScoreFlag> getUnFinishFamilyDayScore(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyScoreList();
	}
	public void finishConfigSystemFamilyScoreTask(TaskSystemScoreFlag task) {
		task.setFamilyDayScoreFlag(1);
		taskSystemScoreFlagMapper.finishFamilyDayScoreTask(task);
		
	}
	
	
	public List<TaskSystemScoreFlag> getUnFinishFamilyPk(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyPkList();
	}
	public void finishConfigSystemFamilyPkTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyPk(1);
		taskSystemScoreFlagMapper.finishFamilyPkTask(task);
		
	}
	
	public List<TaskSystemScoreFlag> getUnFinishFamilyOrder(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyOrderList();
	}
	public void finishConfigSystemFamilyOrderTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyOrder(1);
		taskSystemScoreFlagMapper.finishFamilyOrderTask(task);
		
	}
}
