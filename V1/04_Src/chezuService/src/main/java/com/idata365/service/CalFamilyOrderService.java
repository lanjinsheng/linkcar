package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskFamilyOrder;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.mapper.app.TaskFamilyOrderMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
@Service
public class CalFamilyOrderService {

	@Autowired
   TaskFamilyOrderMapper taskFamilyOrderMapper;
 
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	public boolean calFamilyOrder(TaskFamilyOrder taskFamilyOrder) {
		
		return true;
	}
	
	
	
	public List<TaskFamilyOrder> getFamilyOrderTask(TaskFamilyOrder taskFamilyOrder){
		//先锁定任务
		taskFamilyOrderMapper.lockFamilyOrderTask(taskFamilyOrder);
		//返回任务列表
		return taskFamilyOrderMapper.getFamilyOrderTask(taskFamilyOrder);
	}
	
	public	void updateSuccFamilyOrderTask(TaskFamilyOrder taskFamilyOrder) {
		taskFamilyOrderMapper.updateFamilyOrderSuccTask(taskFamilyOrder);
	}
//	
	public void updateFailFamilyOrderTask(TaskFamilyOrder taskFamilyOrder) {
		taskFamilyOrderMapper.updateFamilyOrderFailTask(taskFamilyOrder);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyOrderMapper.clearLockTask(compareTimes);
	}
	
}
