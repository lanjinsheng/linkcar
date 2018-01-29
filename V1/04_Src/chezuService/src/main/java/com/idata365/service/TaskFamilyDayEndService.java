package com.idata365.service;

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

import com.idata365.entity.TaskFamilyDayEnd;
import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.enums.AchieveEnum;
import com.idata365.mapper.app.TaskFamilyDayEndMapper;
@Service
public class TaskFamilyDayEndService {
public static final String TaskType_FamilyLevel="FamilyLevel";
	@Autowired
   TaskFamilyDayEndMapper taskFamilyDayEndMapper;
	@Autowired
	TaskAchieveAddValueService taskAchieveAddValueService;
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public void doSomeThing() {
	 
	}
	public int maxOrderNo(TaskSystemScoreFlag config) {
		return taskFamilyDayEndMapper.countMaxOrderNo(config);
	}
	@Transactional
	public boolean calLevel(TaskFamilyDayEnd task,int level1,int level2,int level3) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("familyId", task.getFamilyId());
	 double value=task.getTaskValue();
	 if(value<=level1) {//黄金
		 map.put("familyType", 100);
		 taskAchieveAddValueService.addAchieve(task.getFamilyId(), 0d,AchieveEnum.AddGoldFamilyTimes);
	 }else if(value>level1 && value<=level2) {//白银
		 map.put("familyType", 90);
	 }else {//青铜
		 map.put("familyType", 80);
	 }
	 taskFamilyDayEndMapper.updateFamilyLevel(map);
	 return true;
	}
	
	public boolean insertFamilyLevelTask(String daystamp,int order,Long familyId) {
		//家族黄金，白银，青铜等级任务加入
		TaskFamilyDayEnd task=new TaskFamilyDayEnd();
		task.setDaystamp(daystamp);
		task.setTaskType(TaskType_FamilyLevel);
		task.setTaskValue(Double.valueOf(order));
		task.setFamilyId(familyId);
		taskFamilyDayEndMapper.insertTaskFamilyDayEnd(task);
		return true;
	}
	public List<TaskFamilyDayEnd> getFamilyLevelDayEndTask(TaskFamilyDayEnd task){
		task.setTaskType(TaskType_FamilyLevel);
		//先锁定任务
		taskFamilyDayEndMapper.lockFamilyDayEndTask(task);
		//返回任务列表
		return taskFamilyDayEndMapper.getFamilyDayEndTask(task);
	}
	
	public	void updateSuccFamilyDayEndTask(TaskFamilyDayEnd task) {
		taskFamilyDayEndMapper.updateFamilyDayEndSuccTask(task);
	}
//	
	public void updateFailFamilyDayEndTask(TaskFamilyDayEnd task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskFamilyDayEndMapper.updateFamilyDayEndFailTask(task);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyDayEndMapper.clearLockTask(compareTimes);
	}
	
}
