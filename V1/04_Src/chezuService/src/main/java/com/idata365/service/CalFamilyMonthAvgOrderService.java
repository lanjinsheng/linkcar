package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.entity.TaskFamilyDayEnd;
import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskFamilyMonthAvgOrder;
import com.idata365.entity.TaskFamilyMonthOrder;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.mapper.app.TaskFamilyDayEndMapper;
import com.idata365.mapper.app.TaskFamilyDayOrderMapper;
import com.idata365.mapper.app.TaskFamilyMonthAvgOrderMapper;
import com.idata365.mapper.app.TaskFamilyMonthOrderMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
@Service
public class CalFamilyMonthAvgOrderService {

	@Autowired
   TaskFamilyMonthAvgOrderMapper taskFamilyMonthAvgOrderMapper;
	@Autowired
	TaskFamilyDayEndService taskFamilyDayEndService;
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	public boolean calFamilyMonthAvgOrder(TaskFamilyMonthAvgOrder preOrder,TaskFamilyMonthAvgOrder taskFamilyAvgOrder,String daystamp) {
		//family的月分排行
		if(preOrder==null)
		{
			taskFamilyAvgOrder.setOrderNo(1);
		}else {
			if(preOrder.getAvgScore().doubleValue()==taskFamilyAvgOrder.getAvgScore().doubleValue()) {
				taskFamilyAvgOrder.setOrderNo(preOrder.getOrderNo());
			}else {
				taskFamilyAvgOrder.setOrderNo(preOrder.getOrderNo()+1);
			}
		}
		//更新排名
		taskFamilyMonthAvgOrderMapper.updateFamilyMonthAvgOrder(taskFamilyAvgOrder);
		taskFamilyDayEndService.insertFamilyLevelTask(daystamp, taskFamilyAvgOrder.getOrderNo(), taskFamilyAvgOrder.getFamilyId());
		return true;
	}
	
	public TaskFamilyMonthAvgOrder getAvgPre(TaskFamilyMonthAvgOrder taskFamilyAvgOrder) {
		//family的日分排行
		TaskFamilyMonthAvgOrder preOrder=taskFamilyMonthAvgOrderMapper.getPreTaskRecord(taskFamilyAvgOrder);
		return preOrder;
	}
	
	
	public List<TaskFamilyMonthAvgOrder> getFamilyMonthAvgOrderTask(TaskFamilyMonthAvgOrder taskFamilyOrder){
		//先锁定任务
		taskFamilyMonthAvgOrderMapper.lockFamilyMonthAvgOrderTask(taskFamilyOrder);
		//返回任务列表
		return taskFamilyMonthAvgOrderMapper.getFamilyMonthAvgOrderTask(taskFamilyOrder);
	}
	
	public	void updateSuccFamilyMonthAvgOrderTask(TaskFamilyMonthAvgOrder taskFamilyOrder) {
		taskFamilyMonthAvgOrderMapper.updateFamilyMonthAvgOrderSuccTask(taskFamilyOrder);
	}
//	
	public void updateFailFamilyMonthAvgOrderTask(TaskFamilyMonthAvgOrder taskFamilyOrder) {
		taskFamilyMonthAvgOrderMapper.updateFamilyMonthAvgOrderFailTask(taskFamilyOrder);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyMonthAvgOrderMapper.clearLockTask(compareTimes);
	}
	
}
