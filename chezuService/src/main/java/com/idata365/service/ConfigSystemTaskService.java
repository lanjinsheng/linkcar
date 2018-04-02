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
import org.springframework.transaction.annotation.Transactional;

import com.idata365.config.SystemProperties;
import com.idata365.entity.DicGameDay;
import com.idata365.entity.TaskFamilyMonthAvgOrder;
import com.idata365.entity.TaskGameEnd;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.mapper.app.TaskFamilyDayScoreMapper;
import com.idata365.mapper.app.TaskFamilyMonthAvgOrderMapper;
import com.idata365.mapper.app.TaskFamilyMonthOrderMapper;
import com.idata365.mapper.app.DicGameDayMapper;
import com.idata365.mapper.app.TaskFamilyDayOrderMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
import com.idata365.mapper.app.TaskGameEndMapper;
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
	TaskFamilyDayOrderMapper taskFamilyDayOrderMapper;
	@Autowired
	TaskFamilyMonthOrderMapper taskFamilyMonthOrderMapper;
	@Autowired
	TaskFamilyMonthAvgOrderMapper taskFamilyMonthAvgOrderMapper;
	@Autowired
	DicGameDayMapper   dicGameDayMapper;
	@Autowired
	TaskGameEndMapper taskGameEndMapper;
	@Autowired
	SystemProperties systemProperties;
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		LOG.info(dayStr);
		return dayStr;
	}
	@Transactional
	public void configSystemTask(){
	
		//查询竞赛时间
		String dayStamp=getDateStr(-1);
		DicGameDay gameDay=dicGameDayMapper.queryDicGameDay(dayStamp);
		if(gameDay==null) {
			gameDay=new DicGameDay();
			gameDay.setStartDay(dayStamp);
			gameDay.setEndDay(getDateStr(systemProperties.getGameDay()));
			dicGameDayMapper.insertDicGameDay(gameDay);
		}
		
		
		TaskSystemScoreFlag taskSystemScoreFlag=new TaskSystemScoreFlag();
		taskSystemScoreFlag.setDaystamp(dayStamp);
		taskSystemScoreFlag.setStartDay(gameDay.getStartDay());
		taskSystemScoreFlag.setEndDay(gameDay.getEndDay());
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
		 
		//order 任务是通过family日分与月分，pk分都统计好的情况下进行计算的。
		List<TaskSystemScoreFlag> tasks3=taskSystemScoreFlagMapper.getUnInitOrderFlagList();
		for (TaskSystemScoreFlag task:tasks3) {
			taskFamilyDayOrderMapper.delTaskFamilyDayOrder(task.getDaystamp());
			taskFamilyDayOrderMapper.initTaskFamilyDayOrder(task);
			
			String month=task.getDaystamp().replaceAll("-", "").substring(0,6);
			taskFamilyMonthOrderMapper.delTaskFamilyMonthOrder(month);
			taskFamilyMonthOrderMapper.initTaskFamilyMonthOrder(month);
			
			TaskFamilyMonthAvgOrder avgOrder=new TaskFamilyMonthAvgOrder();
			avgOrder.setStartDay(gameDay.getStartDay());
			avgOrder.setEndDay(gameDay.getEndDay());
			taskFamilyMonthAvgOrderMapper.delTaskFamilyMonthAvgOrder(avgOrder);
			taskFamilyMonthAvgOrderMapper.initTaskFamilyMonthAvgOrder(avgOrder);
			task.setTaskFamilyOrderInit(1);
			taskSystemScoreFlagMapper.updateOrderInit(task);
		}
		//game End
		List<TaskSystemScoreFlag> tasks4=taskSystemScoreFlagMapper.getUnInitGameEndList();
		for (TaskSystemScoreFlag task:tasks4) {
			if(dayStamp.equals(task.getEndDay())) {
				//比赛结算时间到了,初始化任务
				taskGameEndMapper.initGameEnd(task);
			}else {
				
			}
			task.setTaskGameEndInit(1);
			taskSystemScoreFlagMapper.updateGameEndInit(task);
		}
	   //game End	 
	}
	
	@Transactional
	public void finishConfigSystemUserScoreTask(TaskSystemScoreFlag task) {
		task.setUserDayScoreFlag(1);
		taskSystemScoreFlagMapper.finishUserDayScoreTask(task);
		
	}
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishUserDayScore(){
		return taskSystemScoreFlagMapper.getUnFinishDayScoreList();
	}
	
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishFamilyDayScore(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyScoreList();
	}
	@Transactional
	public void finishConfigSystemFamilyScoreTask(TaskSystemScoreFlag task) {
		task.setFamilyDayScoreFlag(1);
		taskSystemScoreFlagMapper.finishFamilyDayScoreTask(task);
		
	}
	
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishFamilyPk(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyPkList();
	}
	@Transactional
	public void finishConfigSystemFamilyPkTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyPk(1);
		taskSystemScoreFlagMapper.finishFamilyPkTask(task);
		
	}
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishFamilyDayOrder(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyDayOrderList();
	}
	@Transactional
	public void finishConfigSystemFamilyDayOrderTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyDayOrder(1);
		taskSystemScoreFlagMapper.finishFamilyDayOrderTask(task);
	}
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishFamilyMonthOrder(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyMonthOrderList();
	}
	@Transactional
	public void finishConfigSystemFamilyMonthOrderTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyMonthOrder(1);
		taskSystemScoreFlagMapper.finishFamilyMonthOrderTask(task);
	}
	
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishFamilyMonthAvgOrder(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyMonthAvgOrderList();
	}
	@Transactional
	public void finishConfigSystemFamilyMonthAvgOrderTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyMonthAvgOrder(1);
		taskSystemScoreFlagMapper.finishFamilyMonthAvgOrderTask(task);
	}
	
	@Transactional
	public List<TaskSystemScoreFlag> getUnFinishGameEnd(){
		return taskSystemScoreFlagMapper.getUnFinishGameEndList();
	}
	@Transactional
	public void finishConfigSystemGameEndTask(TaskSystemScoreFlag task) {
		task.setTaskGameEnd(1);
		taskSystemScoreFlagMapper.finishGameEndTask(task);
		
	}
	
	
	
	
	public List<TaskSystemScoreFlag> getUnFinishFamilyLevelDayEnd(){
		return taskSystemScoreFlagMapper.getUnFinishFamilyLevelDayEndList();
	}
	public void finishFamilyLevelDayEndTask(TaskSystemScoreFlag task) {
		task.setTaskFamilyLevelDayEnd(1);
		taskSystemScoreFlagMapper.finishFamilyLevelDayEndTask(task);
	}
	
	public List<TaskSystemScoreFlag> getUnFinishUserBestDriveDayEnd(){
		return taskSystemScoreFlagMapper.getUnFinishUserBestDriveDayEndList();
	}
	public void finishUserBestDriveDayEndTask(TaskSystemScoreFlag task) {
		task.setTaskUserBestDriveDayEnd(1);
		taskSystemScoreFlagMapper.finishUserBestDriveDayEndTask(task);
	}
}
