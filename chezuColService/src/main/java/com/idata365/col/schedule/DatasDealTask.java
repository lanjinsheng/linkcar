package com.idata365.col.schedule;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.idata365.col.api.SSOTools;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.service.DataService;
import com.idata365.col.service.SpringContextUtil;
import com.idata365.col.util.DateTools;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.PhoneGpsUtil;

public class DatasDealTask implements Runnable
{
	private static Logger log = Logger.getLogger(DatasDealTask.class);
	private long userId;
	private long habitId;
	private long  taskId;
    private int hadSensorData;
    private long id;
    private long dealTimes;
	public DatasDealTask(long puserId,long phabitId,long ptaskId,int phadSensorData,long pId, long pdealTimes){
		 this.userId=puserId;
		 this.habitId=phabitId;
		 this.taskId=ptaskId;
		 this.hadSensorData=phadSensorData;
		 this.id=pId;
		 this.dealTimes=pdealTimes;
	}
	@Override
	public void run()
	{
		 log.info("start=="+this.userId+"=="+this.habitId+"=="+this.taskId+"=="+this.hadSensorData);
		 DataService dataService=SpringContextUtil.getBean("dataService", DataService.class);
		 UploadDataStatus status=new UploadDataStatus();
		 status.setTaskFlag(taskId);
		 status.setId(id);
		 try{
			 DriveDataLog d=new DriveDataLog();	
	    	  d.setUserId(userId);
	    	  d.setHabitId(habitId);
	    	  List<DriveDataLog> drives=dataService.listDriveLogByUH(d);
	    	  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    	  for(DriveDataLog drive:drives) {
	    		     StringBuffer json=new StringBuffer();
			         SSOTools.getSSOFile(json,drive.getFilePath());
			         Map<String,Object> jMap=GsonUtils.fromJson(json.toString());
			         if(jMap.get("gpsInfos")!=null) {
			        	 list.addAll((List)jMap.get("gpsInfos"));
			         }
			 }
	    	  if(list.size()>0) {
	    		  Map<String, Object> datasMap= PhoneGpsUtil.getGpsValues(list);
	    		  List<Map<String,Object>> eventList=new ArrayList<Map<String,Object>>();
	    		 List<Map<String,Object>> alarmListJia= (List<Map<String,Object>>)datasMap.get("alarmListJia");
	    		 List<Map<String,Object>> alarmListJian= (List<Map<String,Object>>)datasMap.get("alarmListJian");
	    		 List<Map<String,Object>> alarmListZhuan= (List<Map<String,Object>>)datasMap.get("alarmListZhuan");
	    		 eventList.addAll(alarmListJia);
	    		 eventList.addAll(alarmListJian);
	    		 eventList.addAll(alarmListZhuan);
	    		 String startTime=String.valueOf(datasMap.get("startTime"));
	    		 String endTime=String.valueOf(datasMap.get("endTime"));
	    		 Double maxSpeed=Double.valueOf(datasMap.get("maxSpeed").toString());
	    		 Double avgSpeed=Double.valueOf(datasMap.get("avgSpeed").toString());
	    		 Double distance=Double.valueOf(datasMap.get("distance").toString());
	    		 long driveTimes=Long.valueOf(datasMap.get("driveTimes").toString());
	    		 DriveDataMain data=new DriveDataMain();
	    		 data.setCreateTime(new Date());
	    		 data.setDriveEndTime(endTime);
	    		 data.setDriveStartTime(startTime);
	    		 data.setDriveTimes(driveTimes);
	    		 data.setHabitId(habitId);
	    		 data.setIsPost(0);
	    		 data.setMaxSpeed(BigDecimal.valueOf(maxSpeed));
	    		 data.setPostTime(null);
	    		 data.setUserId(userId);
	    		 data.setAvgSpeed(BigDecimal.valueOf(avgSpeed));
	    		 data.setValidStatus(1);
	    		 data.setDriveDistance(BigDecimal.valueOf(distance));
	    		  //插入数据
	    		 dataService.insertEvents(data, eventList);
	    		 //更新任务数据
	    		 status.setScanStatus(1);
	    		 dataService.updateDataStatusTask(status);	    		 
	    	  }
			}catch(Exception e){
				e.printStackTrace();
				//有异常，进行回滚？
				if(dealTimes>100) {
					status.setInValid(1);
					status.setScanStatus(1);
				}else {
					status.setScanStatus(0);
					status.setInValid(0);
				}
				dataService.updateFailDataStatusTask(status);
			}
		 log.info("end=="+this.userId+"=="+this.habitId+"=="+this.taskId+"=="+this.hadSensorData);
	}

}
