package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.TaskGameEnd;
import com.idata365.mapper.app.TaskGameEndMapper;
@Service
public class CalGameEndService {

	@Autowired
   TaskGameEndMapper taskGameEndMapper;
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public boolean calGameEnd(TaskGameEnd taskGameEnd) {
		 //最后得分的统计增加
		//判断家族的比赛时长
		int dayTimes=taskGameEnd.getDayTimes();
//		【1，5】	1
//		（5，10】	1.05
//		（11，15】	1.1
//		（16，20】	1.15
//		（21，25】	1.2
//		≥26	1.3

        double addRatio=1.0;
		if(dayTimes>=1 && dayTimes<=5) {
			addRatio=1;
		}else if(dayTimes>5 && dayTimes<=10) {
			addRatio=1.05;
		}else if(dayTimes>11 && dayTimes<=15) {
			addRatio=1.1;
		}else if(dayTimes>16 && dayTimes<=20) {
			addRatio=1.15;
		}else if (dayTimes>21 && dayTimes<=25) {
			addRatio=1.2;
		}else if(dayTimes>26) {
			addRatio=1.3;
		}
		taskGameEnd.setAddRatio(addRatio);
		taskGameEndMapper.updateFamilyScore(taskGameEnd);
		return true;
	}
	
	
	public List<TaskGameEnd> geGameEndTask(TaskGameEnd taskGameEnd){
		//先锁定任务
		taskGameEndMapper.lockGameEndTask(taskGameEnd);
		//返回任务列表
		return taskGameEndMapper.getGameEndTask(taskGameEnd);
	}
	
	public	void updateSuccGameEndTask(TaskGameEnd taskGameEnd) {
		taskGameEndMapper.updateGameEndSuccTask(taskGameEnd);
	}
	
	public void updateGameEndFailTask(TaskGameEnd taskGameEnd) {
		if(taskGameEnd.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			taskGameEnd.setTaskStatus(2);
		}else {
			taskGameEnd.setTaskStatus(0);
		}
		taskGameEndMapper.updateGameEndFailTask(taskGameEnd);
	}
//	
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskGameEndMapper.clearLockTask(compareTimes);
	}
	
}
