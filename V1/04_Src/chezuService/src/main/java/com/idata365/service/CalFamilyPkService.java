package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskFamilyPk;
import com.idata365.mapper.app.TaskFamilyPkMapper;
@Service
public class CalFamilyPkService {

	@Autowired
   TaskFamilyPkMapper taskFamilyPkMapper;
 
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	public boolean calFamilyPk(TaskFamilyPk taskFamilyPk) {
		
		return true;
	}
	
	
	
	public List<TaskFamilyPk> getFamilyPkTask(TaskFamilyPk taskFamilyPk){
		//先锁定任务
		taskFamilyPkMapper.lockFamilyPkTask(taskFamilyPk);
		//返回任务列表
		return taskFamilyPkMapper.getFamilyPkTask(taskFamilyPk);
	}
	
	public	void updateSuccFamilyPkTask(TaskFamilyPk taskFamilyPk) {
		taskFamilyPkMapper.updateFamilyPkSuccTask(taskFamilyPk);
	}
//	
	public void updateFailFamilyPkTask(TaskFamilyPk taskFamilyPk) {
		taskFamilyPkMapper.updateFamilyPkFailTask(taskFamilyPk);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyPkMapper.clearLockTask(compareTimes);
	}
	
}
