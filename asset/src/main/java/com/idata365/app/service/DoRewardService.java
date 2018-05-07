package com.idata365.app.service;

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

import com.idata365.app.entity.TaskGeneric;
import com.idata365.app.enums.TaskGenericEnum;
import com.idata365.app.mapper.TaskGenericMapper;
import com.idata365.app.util.GsonUtils;

@Service
public class DoRewardService {

	@Autowired
   TaskGenericMapper taskGenericMapper;
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
//	@Transactional
//	public void doUserDayReward(TaskGeneric task) {
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("genericKey", task.getGenericKey());
//		map.put("taskType", TaskGenericEnum.DoUserDayReward);
//		map.put("priority", 10);
//		Map<String,Object> m=GsonUtils.fromJson(task.getJsonValue());
//		map.putAll(m);
//		taskGenericMapper.initUserDayRewardTask(map);
//	}
	 
	
}
