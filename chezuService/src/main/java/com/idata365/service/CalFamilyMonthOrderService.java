package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.TaskFamilyDayOrder;
import com.idata365.entity.TaskFamilyMonthOrder;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.mapper.app.TaskFamilyDayOrderMapper;
import com.idata365.mapper.app.TaskFamilyMonthOrderMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;

/**
 * 
    * @ClassName: CalFamilyMonthOrderService
    * @Description: TODO(这做废，该计算可以不需要)
    * @author LanYeYe
    * @date 2018年5月3日
    *
 */
@Service
public class CalFamilyMonthOrderService {

	@Autowired
   TaskFamilyMonthOrderMapper taskFamilyMonthOrderMapper;
 
	
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public boolean calFamilyMonthOrder(TaskFamilyMonthOrder preOrder,TaskFamilyMonthOrder taskFamilyOrder) {
		//family的月分排行
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
		taskFamilyMonthOrderMapper.updateFamilyMonthOrder(taskFamilyOrder);
		return true;
	}
	@Transactional
	public TaskFamilyMonthOrder getPre(TaskFamilyMonthOrder taskFamilyOrder) {
		//family的日分排行
		TaskFamilyMonthOrder preOrder=taskFamilyMonthOrderMapper.getPreTaskRecord(taskFamilyOrder);
		return preOrder;
	}
	
	@Transactional
	public List<TaskFamilyMonthOrder> getFamilyMonthOrderTask(TaskFamilyMonthOrder taskFamilyOrder){
		//先锁定任务
		taskFamilyMonthOrderMapper.lockFamilyMonthOrderTask(taskFamilyOrder);
		//返回任务列表
		return taskFamilyMonthOrderMapper.getFamilyMonthOrderTask(taskFamilyOrder);
	}
	@Transactional
	public	void updateSuccFamilyMonthOrderTask(TaskFamilyMonthOrder taskFamilyOrder) {
		taskFamilyMonthOrderMapper.updateFamilyMonthOrderSuccTask(taskFamilyOrder);
	}
//	
	@Transactional
	public void updateFailFamilyMonthOrderTask(TaskFamilyMonthOrder taskFamilyOrder) {
		taskFamilyMonthOrderMapper.updateFamilyMonthOrderFailTask(taskFamilyOrder);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyMonthOrderMapper.clearLockTask(compareTimes);
	}
	
}
