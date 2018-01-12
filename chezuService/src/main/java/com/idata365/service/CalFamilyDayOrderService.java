package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.mapper.app.TaskFamilyDayOrderMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
@Service
public class CalFamilyDayOrderService {

	@Autowired
   TaskFamilyDayOrderMapper taskFamilyDayOrderMapper;
 
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	public boolean calFamilyDayOrder(TaskFamilyDayOrder preOrder,TaskFamilyDayOrder taskFamilyOrder) {
		//family的日分排行
		if(preOrder==null)
		{
			taskFamilyOrder.setOrderNo(1);
		}else {
			if(preOrder.getScore().doubleValue()==taskFamilyOrder.getScore().doubleValue()) {
				taskFamilyOrder.setOrderNo(preOrder.getOrderNo());
			}else {
				taskFamilyOrder.setOrderNo(preOrder.getOrderNo()+1);
			}
		}
		//更新排名
		taskFamilyDayOrderMapper.updateFamilyDayOrder(taskFamilyOrder);
		return true;
	}
	
	public TaskFamilyDayOrder getPre(TaskFamilyDayOrder taskFamilyOrder) {
		//family的日分排行
		TaskFamilyDayOrder preOrder=taskFamilyDayOrderMapper.getPreTaskRecord(taskFamilyOrder);
		return preOrder;
	}
	
	
	public List<TaskFamilyDayOrder> getFamilyDayOrderTask(TaskFamilyDayOrder taskFamilyOrder){
		//先锁定任务
		taskFamilyDayOrderMapper.lockFamilyDayOrderTask(taskFamilyOrder);
		//返回任务列表
		return taskFamilyDayOrderMapper.getFamilyDayOrderTask(taskFamilyOrder);
	}
	
	public	void updateSuccFamilyDayOrderTask(TaskFamilyDayOrder taskFamilyOrder) {
		taskFamilyDayOrderMapper.updateFamilyDayOrderSuccTask(taskFamilyOrder);
	}
//	
	public void updateFailFamilyDayOrderTask(TaskFamilyDayOrder taskFamilyOrder) {
		taskFamilyDayOrderMapper.updateFamilyDayOrderFailTask(taskFamilyOrder);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyDayOrderMapper.clearLockTask(compareTimes);
	}
	
}
