package com.idata365.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.mapper.app.CarpoolMapper;
import com.idata365.util.DateTools;

@Service
public class CarpoolService extends BaseService<CalScoreUserDayService>{

@Autowired
CarpoolMapper carpoolMapper;
@Transactional
public void clearTask(String daystamp){
	 DateTools.getAddMinuteDateTime(daystamp,1,"yyyy-MM-dd");
	carpoolMapper.clearCarPool(daystamp+" 00:00:00");
}
}
