package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.TaskUserDayEnd;
import com.idata365.enums.AchieveEnum;
import com.idata365.mapper.app.TaskUserDayEndMapper;
@Service
public class TaskUserDayEndService {
public static final String TaskType_JugeBestDrive="JugeBestDrive";
	@Autowired
   TaskUserDayEndMapper taskUserDayEndMapper;
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
	@Transactional
	public boolean calBestDrive(TaskUserDayEnd task) {
		if(task.getTaskValue().doubleValue()==0d) {
			//安全驾驶了
			taskAchieveAddValueService.addAchieve(task.getUserId(), 0d, AchieveEnum.AddBestDriverTimes);
		}else {
			//不安全
		}
		return true;
	}
	public void addUserEndDayIsBestDrive(Long userId,Double value,String daystamp) {
		//value==0 没有违规 >0 则存在违规
			TaskUserDayEnd task=new TaskUserDayEnd();
			task.setDaystamp(daystamp);
			task.setUserId(userId);
			task.setTaskType(TaskType_JugeBestDrive);
			task.setTaskValue(value);
			taskUserDayEndMapper.insertTaskUserDayEnd(task);
	}
	@Transactional
	public List<TaskUserDayEnd> getUserBestDriveDayEndTask(TaskUserDayEnd task){
		task.setTaskType(TaskType_JugeBestDrive);
		//先锁定任务
		taskUserDayEndMapper.lockUserDayEndTask(task);
		//返回任务列表
		return taskUserDayEndMapper.getUserDayEndTask(task);
	}
	@Transactional
	public	void updateSuccUserDayEndTask(TaskUserDayEnd task) {
		taskUserDayEndMapper.updateUserDayEndSuccTask(task);
	}
//	
	@Transactional
	public void updateFailUserDayEndTask(TaskUserDayEnd task) {
		if(task.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			task.setTaskStatus(2);
		}else {
			task.setTaskStatus(0);
		}
		taskUserDayEndMapper.updateUserDayEndFailTask(task);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskUserDayEndMapper.clearLockTask(compareTimes);
	}
	
}
