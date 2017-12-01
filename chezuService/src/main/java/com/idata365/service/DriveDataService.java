package com.idata365.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DriveData;
import com.idata365.mapper.DriveDataEventMapper;
import com.idata365.mapper.DriveDataMapper;


@Service
public class DriveDataService extends BaseService<DriveDataService>{
	private final static Logger LOG = LoggerFactory.getLogger(DriveDataService.class);
	@Autowired
	DriveDataMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
	
	
	public DriveDataService() {
		LOG.info("DriveDataService DriveDataService DriveDataService DriveDataService");
	}
	 
	/**
	 * 
	    * @Title: insertEvents
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param data
	    * @param @param eventList    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void  insertEvents(Map<String,Object> drive,List<Map<String,Object>> eventList) {
		drive.put("driveDataMainId", drive.get("id"));
		drive.remove("id");
		driveDataMainMapper.insertDriveMain(drive);
		if(drive.get("id")!=null) {
			LOG.info("driveId:"+drive.get("id"));
			Map<String,Object> alarmMap=new HashMap<String,Object>();
			alarmMap.put("driveDataMainId", drive.get("driveDataMainId"));
			alarmMap.put("list", eventList);
			driveDataEventMapper.insertDriveEvent(alarmMap);
		}else {
			LOG.info("重复数据"+drive.get("driveDataMainId"));
		}
		
	}
}
