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

import com.idata365.col.entity.DriveDataEvent;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.DriveDataStartLog;
import com.idata365.col.entity.SensorDataLog;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.entity.UserDevice;
import com.idata365.col.mapper.DriveDataEventMapper;
import com.idata365.col.mapper.DriveDataLogMapper;
import com.idata365.col.mapper.DriveDataMainMapper;
import com.idata365.col.mapper.DriveDataStartLogMapper;
import com.idata365.col.mapper.SensorDataLogMapper;
import com.idata365.col.mapper.UploadDataStatusMapper;
import com.idata365.col.mapper.UserDeviceMapper;
import com.idata365.col.remote.ChezuAccountService;
import com.idata365.col.util.DateTools;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.ResultUtils;
import com.idata365.col.util.SignUtils;

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
	@Autowired
	UserDeviceMapper userDeviceMapper;
	@Autowired
	DriveDataStartLogMapper driveDataStartLogMapper;
	@Autowired
	ChezuAccountService chezuAccountService;
	
	public DataService() {
		LOG.info("DataService DataService DataService DataService");
	}
	public DriveDataLog getDriveLog(Long id) {
		return driveDataLogMapper.getDriveDataLog(id);
	}
	
	@Transactional
	public List<UploadDataStatus> getUploadDataDemo(Map<String,Object> m){
		return uploadDataStatusMapper.getUploadDataDemo(m);
	}
	
	@Transactional
	public boolean addDeviceUserInfo(String deviceToken,long userId){
		 UserDevice dl=new UserDevice();
		 String date=DateTools.getCurDate();
		 String remark= "{%s 用户:%s 设备号:%s}";
		 remark=String.format(remark, date,String.valueOf(userId),deviceToken);
		 dl.setRemark(remark);
		 dl.setDeviceToken(deviceToken);
		 dl.setUserId(userId);
		 userDeviceMapper.insertUserDevice(dl);
		 return true;
	}
	/**
	 * 
	    * @Title: insertStartEventLog
	    * @Description: TODO(插入行程开始事件)
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
   public void insertStartEventLog(DriveDataStartLog startLog) {
	   driveDataStartLogMapper.insertStartData(startLog);
   }
	
	/**
	 * 
	    * @Title: listDriveLogByUH
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param log
	    * @param @return    参数
	    * @return List<DriveDataLog>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public List<DriveDataLog>  listDriveLogByUH(DriveDataLog log) {
		  List<DriveDataLog> list= driveDataLogMapper.listDriveLogByUH(log);
		  return list;
	}
	@Transactional
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
	public void insertDriveLog(DriveDataLog log,String deviceToken) {
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
			status.setDeviceToken(deviceToken);
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
	@Transactional
	public String listPageDriveLog(Map<String,Object> map) {
		if(map.get("userId")!=null && !map.get("userId").equals("")) {
			 map.put("userId", Long.valueOf(map.get("userId").toString()));
		}
		else if(map.get("phone")!=null && !map.get("phone").equals("")) {
			 String phone=String.valueOf(map.get("phone"));
			 long userId=chezuAccountService.getUserIdByPhone(phone, SignUtils.encryptHMAC(phone));
			 map.put("userId", userId);
		 }
		  List<DriveDataLog> list= driveDataLogMapper.listPageDriveLog(map);
			StringBuffer sb = new StringBuffer("");
			sb.append(ResultUtils.toJson(list));
			ResultUtils.putSuccess(map);
			return sb.toString();
	}
	@Transactional
	public String listPageDriveLogTest(Map<String,Object> map) {
		  List<DriveDataLog> list= driveDataLogMapper.listPageDriveLogTest(map);
			StringBuffer sb = new StringBuffer("");
			sb.append(ResultUtils.toJson(list));
			ResultUtils.putSuccess(map);
			return sb.toString();
	}
	
	
	@Transactional
	public String listPageDriveMainTest(Map<String,Object> map) {
		  List<DriveDataMain> list= driveDataMainMapper.listPageDriveMainTest(map);
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
	@Transactional
	public List<UploadDataStatus>  getUploadDataStatusTask(UploadDataStatus status) {
		   uploadDataStatusMapper.lockUploadStatusTask(status);
		  List<UploadDataStatus> list= uploadDataStatusMapper.getUploadDataStatusTask(status);
		  return list;
	}
	@Transactional
	public void updateDataStatusTask(UploadDataStatus status) {
		   uploadDataStatusMapper.updateUploadStatusTask(status);
		  
	}
	@Transactional
	public void updateFailDataStatusTask(UploadDataStatus status) {
		   uploadDataStatusMapper.updateFailUploadStatusTask(status);
		  
	}
	
	
	/**
	 * 
	 */
	@Transactional
	public void  insertEvents(DriveDataMain data,List<Map<String,Object>> eventList) {
		driveDataMainMapper.insertDataLog(data);
		if(data.getId()!=null && data.getId()>0) {
			if(eventList!=null && eventList.size()>0) {
				Map<String,Object> alarmMap=new HashMap<String,Object>();
				alarmMap.put("driveDataMainId", data.getId());
				alarmMap.put("list", eventList);
				try {
				driveDataEventMapper.insertDriveEvent(alarmMap);
				}catch(Exception e) {
					System.out.println(GsonUtils.toJson(alarmMap,true));
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	    * @Title: getSendDriveTask
	    * @Description: TODO(先锁定,后调用)
	    * @param @param drive    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
//	public List<DriveDataMain>  getSendDriveTask(DriveDataMain drive) {
//		    driveDataMainMapper.lockSendDriveTask(drive);
//		  List<DriveDataMain> list= driveDataMainMapper.getSendDriveTask(drive);
//		  return list;
//	}
//	
//	public void updateSuccSendDriveTask(DriveDataMain drive) {
//		drive.setIsPost(1);
//		driveDataMainMapper.updateSuccSendDriveTask(drive);
//	}
//	public void updateFailSendDriveTask(DriveDataMain drive) {
//		drive.setIsPost(0);
//		driveDataMainMapper.updateFailSendDriveTask(drive);
//	}
	
	@Transactional
	public List<DriveDataEvent> listDriveEventByMainId(DriveDataMain drive){
		return driveDataEventMapper.listDriveEventByMainId(drive);
	}
	
	@Transactional
	public void clearLockTask(){
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
//		driveDataMainMapper.clearLockTask(compareTimes);
		uploadDataStatusMapper.clearLockTask(compareTimes);
	}
	
	
}
