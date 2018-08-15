package com.idata365.col.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.col.api.QQSSOTools;
import com.idata365.col.api.SSOTools;
import com.idata365.col.config.ColProperties;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.TaskDriveDataMain;
import com.idata365.col.mapper.TaskDriveDataMainMapper;
import com.idata365.col.util.DateTools;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.PhoneGpsUtil;


@Service
public class TaskDriveDataMainService {

	@Autowired
	TaskDriveDataMainMapper taskDriveDataMainMapper;
	@Autowired
	DataService dataService;
	@Autowired
	YingyanService yingyanService;
	@Autowired
	ColProperties colProperties;
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public boolean doTaskDriveDataMain(TaskDriveDataMain data) {
		DriveDataLog d=new DriveDataLog();	
  	  d.setUserId(data.getUserId());
  	  d.setHabitId(data.getHabitId());
  	  List<DriveDataLog> drives=dataService.listDriveLogByUH(d);
  	  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
  	  for(DriveDataLog drive:drives) {
  		     StringBuffer json=new StringBuffer();
  		     if(drive.getFilePath().endsWith("_Q")) {
  		    	 QQSSOTools.getSSOFile(json, drive.getFilePath());
  		     }else {
  		    	 SSOTools.getSSOFile(json,drive.getFilePath());
  		     }
		         Map<String,Object> jMap=GsonUtils.fromJson(json.toString());
		         if(jMap.get("gpsInfos")!=null) {
		        	 list.addAll((List)jMap.get("gpsInfos"));
		         }
		 }
  	  if(list.size()>0) {
  		 List<Map<String, String>> datas= PhoneGpsUtil.dealOrderGps(list);
  		  Map<String, Object> datasMap= PhoneGpsUtil.getGpsValues(datas,"userId="+data.getUserId()+"==habitId="+data.getHabitId(),colProperties);
  		  List<Map<String,Object>> eventList=new ArrayList<Map<String,Object>>();
  		 List<Map<String,Object>> alarmListJia= (List<Map<String,Object>>)datasMap.get("alarmListJia");
  		 List<Map<String,Object>> alarmListJian= (List<Map<String,Object>>)datasMap.get("alarmListJian");
  		 List<Map<String,Object>> alarmListZhuan= (List<Map<String,Object>>)datasMap.get("alarmListZhuan");
  		  List<Map<String,Object>> alarmListChao=yingyanService.dealListGaode(datas);
  		 eventList.addAll(alarmListJia);
  		 eventList.addAll(alarmListJian);
  		 eventList.addAll(alarmListZhuan);
  		 eventList.addAll(alarmListChao);
  		 String startTime=String.valueOf(datasMap.get("startTime"));
  		 String endTime=String.valueOf(datasMap.get("endTime"));
  		 Double maxSpeed=Double.valueOf(datasMap.get("maxSpeed").toString());
  		 Double avgSpeed=Double.valueOf(datasMap.get("avgSpeed").toString());
  		 Double distance=Double.valueOf(datasMap.get("distance").toString());
  		 long driveTimes=Long.valueOf(datasMap.get("driveTimes").toString());
	    		 //识别驾驶标签
	    		 if(driveTimes<180) {//180s内的驾驶行为忽略
	    			 data.setLabelFlag("短时");
	    		 }else if(maxSpeed<7 && driveTimes<900) {
	    			 data.setLabelFlag("电动车/自行车");
	    		 }else   if(avgSpeed>41){
	    			 data.setLabelFlag("高铁");
	    		 }else {
	    			 data.setLabelFlag("小车");
	    		 }
  		 data.setCreateTime(new Date());
  		 data.setDriveEndTime(endTime);
  		 data.setDriveStartTime(startTime);
  		 data.setDriveTimes(driveTimes);
  		 data.setMaxSpeed(BigDecimal.valueOf(maxSpeed));
  		 data.setAvgSpeed(BigDecimal.valueOf(avgSpeed));
  		 data.setDriveDistance(BigDecimal.valueOf(distance));
  		 data.setSpeed120To129Times(Integer.valueOf(datasMap.get("speed120To129Times").toString()));
  		 data.setSpeed130To139Times(Integer.valueOf(datasMap.get("speed130To139Times").toString()));
  		 data.setSpeed140To149Times(Integer.valueOf(datasMap.get("speed140To149Times").toString()));
  		 data.setSpeed150To159Times(Integer.valueOf(datasMap.get("speed150To159Times").toString()));
  		 data.setSpeed160UpTimes(Integer.valueOf(datasMap.get("speed160UpTimes").toString()));
  		 data.setSpeedUpTimes(alarmListJia.size());
  		 data.setBrakeTimes(alarmListJian.size());
  		 data.setTurnTimes(alarmListZhuan.size());
  		 data.setOverspeedTimes(alarmListChao.size());
  	  }
		return true;
	}
	
	
	@Transactional
	public List<TaskDriveDataMain> getDataMainTask(TaskDriveDataMain taskDriveDataMain){
		//先锁定任务
		taskDriveDataMainMapper.lockDriveDataMainTask(taskDriveDataMain);
		//返回任务列表
		return taskDriveDataMainMapper.getDriveDataMainTask(taskDriveDataMain);
	}
	@Transactional
	public	void updateDriveDataMainSuccTask(TaskDriveDataMain taskDriveDataMain) {
		taskDriveDataMainMapper.updateDriveDataMainSuccTask(taskDriveDataMain);
	}
//	
	@Transactional
	public void updateDriveDataMainFailTask(TaskDriveDataMain taskDriveDataMain) {
		if(taskDriveDataMain.getFailTimes()>5) {
			taskDriveDataMain.setTaskStatus(2);
		}
		taskDriveDataMainMapper.updateDriveDataMainFailTask(taskDriveDataMain);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskDriveDataMainMapper.clearLockTask(compareTimes);
	}
	
}
