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

import com.idata365.entity.TaskSystemScoreFlag;
import com.idata365.mapper.app.TaskFamilyDayScoreMapper;
import com.idata365.mapper.app.TaskSystemScoreFlagMapper;
@Service
public class ConfigSystemTaskService  extends BaseService<ConfigSystemTaskService>{
	private static final Logger LOG = LoggerFactory.getLogger(ConfigSystemTaskService.class);
	@Autowired
	TaskFamilyDayScoreMapper taskFamilyDayScoreMapper;
	@Autowired
    TaskSystemScoreFlagMapper taskSystemScoreFlagMapper;
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		LOG.info(dayStr);
		return dayStr;
	}
	public void configSystemTask(){
		String dayStamp=getDateStr(-1);
		TaskSystemScoreFlag taskSystemScoreFlag=new TaskSystemScoreFlag();
		taskSystemScoreFlag.setDaystamp(dayStamp);
		int insert=taskSystemScoreFlagMapper.insertSystemScoreFlag(taskSystemScoreFlag);
		List<TaskSystemScoreFlag> tasks=taskSystemScoreFlagMapper.getUnInitSystemScoreFlagList();
		
		for (TaskSystemScoreFlag task:tasks) {
			Map<String,Object> map=new HashMap<String,Object>();
			String day=task.getDaystamp().replace("-", "");
			map.put("startTime", day+"000000");
			map.put("endTime", day+"235959");
			taskFamilyDayScoreMapper.insertTaskFamilyDayScoreByTime(map);
			task.setTaskFamilyInit(1);
			taskSystemScoreFlagMapper.updateSystemScoreFlag(task);
		}
		 
	}
	
}
