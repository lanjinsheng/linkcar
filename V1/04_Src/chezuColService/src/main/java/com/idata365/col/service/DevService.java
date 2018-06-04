package com.idata365.col.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.col.entity.AppConfigLogs;
import com.idata365.col.entity.DevDriveLogs;
import com.idata365.col.mapper.AppConfigLogsMapper;
import com.idata365.col.mapper.DevDriveLogsMapper;
@Service
public class DevService extends BaseService<DevService>{
	private final static Logger LOG = LoggerFactory.getLogger(DevService.class);
	@Autowired
	DevDriveLogsMapper devDriveLogsMapper;
	@Autowired
	AppConfigLogsMapper appConfigLogsMapper;
	
	@Transactional
	public void insertDevDriveLog(DevDriveLogs log){
		devDriveLogsMapper.insertDevLogs(log);
	}
	@Transactional
	public void insertAppConfigLog(AppConfigLogs log){
		appConfigLogsMapper.insertAppConfigLogs(log);
	}
	
}
