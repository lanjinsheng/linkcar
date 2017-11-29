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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.SensorDataLog;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.mapper.DriveDataLogMapper;
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
	
	public DataService() {
		LOG.info("DataServiceDataServiceDataServiceDataService");
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
			status.setTaskFlag("0");
			status.setSensorUploadStatus(0);
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
	
	
	public List<UploadDataStatus>  getUploadDataStatus(Map<String,Object> map) {
		  List<UploadDataStatus> list= uploadDataStatusMapper.getUploadDataStatus(map);
		 
			return list;
	}
	
}
