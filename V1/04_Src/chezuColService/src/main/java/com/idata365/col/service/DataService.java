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

import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.SensorDataLog;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.mapper.DriveDataEventMapper;
import com.idata365.col.mapper.DriveDataLogMapper;
import com.idata365.col.mapper.DriveDataMainMapper;
import com.idata365.col.mapper.SensorDataLogMapper;
import com.idata365.col.mapper.UploadDataStatusMapper;
import com.idata365.col.util.ResultUtils;

@Service
public class DataService extends BaseService<DataService>{
	private final static Logger LOG = LoggerFactory.getLogger(DataService.class);
	@Autowired
	DriveDataLogMapper driveDataLogMapper;
	@Autowired
	UploadDataStatusMapper uploadDataStatusMapper;
	@Autowired
	SensorDataLogMapper sensorDataLogMapper;
	
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
	
	
	public DataService() {
		LOG.info("DataService DataService DataService DataService");
	}
	public DriveDataLog getDriveLog(Long id) {
		return driveDataLogMapper.getDriveDataLog(id);
	}
	
	
	public List<DriveDataLog>  listDriveLogByUH(DriveDataLog log) {
		  List<DriveDataLog> list= driveDataLogMapper.listDriveLogByUH(log);
		  return list;
	}
	
	public List<SensorDataLog>  listSensorByUH(DriveDataLog log) {
		  List<SensorDataLog> list= sensorDataLogMapper.listSensorLogByUH(log);
		  return list;
	}
	
	/**
	 * 
	    * @Title: insertDriveLog
	    * @Description: TODO(插入日志)
	    * @param @param log    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void insertDriveLog(DriveDataLog log) {
		driveDataLogMapper.insertDataLog(log);
		if(log.getIsEnd()==1) {
			//插入结束任务
			UploadDataStatus status=new UploadDataStatus();
			status.setCreateTime(new Date());
			status.setHabitId(log.getHabitId());
			status.setScanStatus(0);
			status.setUserId(log.getUserId());
			status.setHadSensorData(log.getHadSensorData());
			status.setTaskFlag(0L);
			status.setCreateTimeSS(System.currentTimeMillis());
			status.setSensorUploadStatus(0);
			if(status.getHadSensorData()==0) {
				status.setComplete(1);
			}else {
				status.setComplete(0);
			}
			uploadDataStatusMapper.insertUploadDataStatus(status);
		}
	}
	/**
	 * 
	    * @Title: delDriveLog
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param log    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void delDriveLog(DriveDataLog log) {
		driveDataLogMapper.delDataLog(log);
	}
	/**
	 * 
	    * @Title: insertSensorLog
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param log    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void insertSensorLog(SensorDataLog log) {
		sensorDataLogMapper.insertSensorLog(log);
		if(log.getIsEnd()==1) {
			//插入结束任务
			UploadDataStatus status=new UploadDataStatus();
			status.setHabitId(log.getHabitId());
			status.setScanStatus(0);
			status.setUserId(log.getUserId());
			status.setHadSensorData(0);
			uploadDataStatusMapper.updateSensorUploadStatus(status);
		}
	}
	/**
	 * 
	    * @Title: delLog
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param log    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public void delSensorLog(DriveDataLog log) {
		driveDataLogMapper.delDataLog(log);
	}
	
	public String listPageDriveLog(Map<String,Object> map) {
		  List<DriveDataLog> list= driveDataLogMapper.listPageDriveLog(map);
			StringBuffer sb = new StringBuffer("");
			sb.append(ResultUtils.toJson(list));
			ResultUtils.putSuccess(map);
			return sb.toString();
	}
	
	/**
	 * 
	    * @Title: lockUploadStatusTask
	    * @Description: TODO(先锁定,后调用)
	    * @param @param status    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public List<UploadDataStatus>  getUploadDataStatusTask(UploadDataStatus status) {
		   uploadDataStatusMapper.lockUploadStatusTask(status);
		  List<UploadDataStatus> list= uploadDataStatusMapper.getUploadDataStatusTask(status);
		  return list;
	}
	
	public void updateDataStatusTask(UploadDataStatus status) {
		   uploadDataStatusMapper.updateUploadStatusTask(status);
		  
	}
	
	/**
	 * 
	 */
	public void  insertEvents(DriveDataMain data,List<Map<String,Object>> eventList) {
		driveDataMainMapper.insertDataLog(data);
		Map<String,Object> alarmMap=new HashMap<String,Object>();
		alarmMap.put("driveDataMainId", data.getId());
		alarmMap.put("list", eventList);
		driveDataEventMapper.insertDriveEvent(alarmMap);
	}
}
