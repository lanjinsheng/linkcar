package com.idata365.app.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.DicGameDay;
import com.idata365.app.mapper.DicFamilyTypeMapper;
import com.idata365.app.mapper.DicGameDayMapper;
import com.idata365.app.mapper.DicNotifyMapper;

@Service
public class DicService extends BaseService<DicService>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DicService.class);
	
	@Autowired
	private DicFamilyTypeMapper dicFamilyTypeMapper;
	@Autowired
	DicGameDayMapper dicGameDayMapper;
	@Autowired
	DicNotifyMapper dicNotifyMapper;
	
	@Transactional
	public List<DicFamilyType> getDicFamilyType()
	{
		return dicFamilyTypeMapper.getDicFamilyType(null);
	}
	@Transactional
	public String getNotify() {
		  Map<String,String> map=dicNotifyMapper.getLatestNotify();
		  return map.get("notifyText");
	}

	
	@Transactional
	public DicGameDay getDicGameDay(String daystamp)
	{
		return dicGameDayMapper.getGameDicByDaystamp(daystamp);
	}

	public String getSurPlusDays() {
		String dayStr = getCurrentDayStr();
		Map<String, String> map = dicFamilyTypeMapper.getSurPlusDays(dayStr);
		String endDay = map.get("endDay");
		long ed = Long.valueOf(endDay.replaceAll("-", "")) - Long.valueOf(dayStr.replaceAll("-", ""));

		return ed + "";
	}


	
	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
	
}
