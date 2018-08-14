package com.idata365.app.service;

import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.DicGameDay;
import com.idata365.app.entity.DicUserMission;
import com.idata365.app.entity.v2.DicComponent;
import com.idata365.app.mapper.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DicService extends BaseService<DicService> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DicService.class);

	@Autowired
	private DicFamilyTypeMapper dicFamilyTypeMapper;
	@Autowired
	DicGameDayMapper dicGameDayMapper;
	@Autowired
	DicNotifyMapper dicNotifyMapper;
	@Autowired
	DicUserMissionMapper dicUserMissionMapper;
	@Autowired
	DicCarMapper dicCarMapper;
	@Autowired
	DicComponentMapper dicComponentMapper;
	@Autowired
	DicRuleMapper dicRuleMapper;
	@Autowired
	DicAdMapMapper dicAdMapMapper;

	@Transactional
	public List<DicFamilyType> getDicFamilyType() {
		return dicFamilyTypeMapper.getDicFamilyType(null);
	}
	@Transactional
	public List<DicCar> getDicCar() {
		return dicCarMapper.getDicCar(null);
	}
	
	@Transactional
	public List<DicUserMission> getDicUserMission() {
		return dicUserMissionMapper.getAllDicUserMission();
	}
	@Transactional
	public List<DicComponent> getDicComponent() {
		return dicComponentMapper.getDicComponent(null);
	}
	
	@Transactional
	public String getNotify() {
		Map<String, String> map = dicNotifyMapper.getLatestNotify();
		return map.get("notifyText");
	}

	@Transactional
	public DicGameDay getDicGameDay(String daystamp) {
		return dicGameDayMapper.getGameDicByDaystamp(daystamp);
	}

	public String getSurPlusDays() throws ParseException {
		String dayStr = getCurrentDayStr();
		Map<String, String> map = dicFamilyTypeMapper.getSurPlusDays(dayStr);
		String endDay = map.get("endDay");
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dayStr);
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDay);

		long day = ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000))+1;

		return "距离"+endDay+"赛季结束还剩："+day + "天";
	}

	private String getCurrentDayStr() {
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}

	public List<Map<String, String>> playHelper() {
		List<Map<String, String>> list = dicNotifyMapper.playHelper();
		return list;
	}
	
	public List<Map<String, String>> getRulesByType(int ruleType) {
		List<Map<String, String>> list = dicRuleMapper.getRulesByType(ruleType);
		return list;
	}
	
	public List<Map<String, String>> getDicAdMap() {
		List<Map<String, String>> list = dicAdMapMapper.getDicAdMap();
		return list;
	}

}
