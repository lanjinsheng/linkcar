package com.idata365.app.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.TaskSystemFlag;
import com.idata365.app.entity.TaskUserLottery;
import com.idata365.app.mapper.TaskSystemFlagMapper;
import com.idata365.app.mapper.TaskUserLotteryMapper;

@Service
public class TaskSystemFlagService  extends BaseService<TaskSystemFlagService>{
	private static final Logger LOG = LoggerFactory.getLogger(TaskSystemFlagService.class);
	@Autowired
	TaskSystemFlagMapper taskSystemFlagMapper;
	@Autowired
	TaskUserLotteryMapper taskUserLotteryMapper;
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
		String dayStamp=getDateStr(0);
		TaskSystemFlag taskSystemFlag=new TaskSystemFlag();
		taskSystemFlag.setDaystamp(dayStamp);
		int insert=taskSystemFlagMapper.initTaskSystemFlag(taskSystemFlag);
		List<TaskSystemFlag> tasks=taskSystemFlagMapper.getUnInitLotteryFlagList(taskSystemFlag);
		for (TaskSystemFlag task:tasks) {
			TaskUserLottery lottery=new TaskUserLottery();
			lottery.setDaystamp(task.getDaystamp());
			taskUserLotteryMapper.initTaskUserLottery(lottery);
			task.setTaskLotteryInit(1);
			taskSystemFlagMapper.finishInitLottery(task);
		}
	}
}
