package com.idata365.col.schedule;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.idata365.col.api.QQSSOTools;
import com.idata365.col.api.SSOTools;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.service.DataService;
import com.idata365.col.service.SpringContextUtil;
import com.idata365.col.service.YingyanService;
import com.idata365.col.util.DateTools;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.OverspeedUtil;
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
//		 YingyanService yingyanService=SpringContextUtil.getBean("yingyanService", YingyanService.class);
		 UploadDataStatus status=new UploadDataStatus();
		 status.setTaskFlag(taskId);
		 status.setId(id);
		 try{
			 DriveDataLog d=new DriveDataLog();	
	    	  d.setUserId(userId);
	    	  d.setHabitId(habitId);
	    	  List<DriveDataLog> drives=dataService.listDriveLogByUH(d);
	    	  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    	  List<Map<String,Object>> jkList=new ArrayList<Map<String,Object>>();
	    	  for(DriveDataLog drive:drives) {
	    		  log.info("进入drive for");
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
			         try {
				         if(jMap.get("overspeedGPSInfo")!=null) {
				        	 jkList.addAll((List)jMap.get("overspeedGPSInfo"));
				         }
			         }catch(Exception e) {
			        	 log.error(e);
			        	 e.printStackTrace();
			         }
			 }
	    	  if(list.size()>0) {
	    		  Map<String, Object> datasMap= PhoneGpsUtil.getGpsValues(list,"userId="+userId+"==habitId="+habitId);
	    		  List<Map<String,Object>> eventList=new ArrayList<Map<String,Object>>();
	    		 List<Map<String,Object>> alarmListJia= (List<Map<String,Object>>)datasMap.get("alarmListJia");
	    		 List<Map<String,Object>> alarmListJian= (List<Map<String,Object>>)datasMap.get("alarmListJian");
	    		 List<Map<String,Object>> alarmListZhuan= (List<Map<String,Object>>)datasMap.get("alarmListZhuan");
	    		  List<Map<String,Object>> alarmListChao=OverspeedUtil.dealOverSpeed(jkList);
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
	    		 String day1=endTime.substring(0,10).replaceAll("-", "");
	    		 String day2=DateTools.getYYYYMMDD();
	    		 DriveDataMain data=new DriveDataMain();
	    		 if(Integer.valueOf(day2).intValue()!=Integer.valueOf(day1)) {
	    			 data.setValidStatus(-1);
	    			 data.setLabelFlag("隔天数据");
	    		 }else if(list.size()<60){
	    			 data.setValidStatus(-1);
	    			 data.setLabelFlag("点数小于60");
	    		 }else {
		    		 //识别驾驶标签
		    		 if(driveTimes<180) {//180s内的驾驶行为忽略
		    			 data.setValidStatus(-1);
		    			 data.setLabelFlag("短时");
		    		 }else if(maxSpeed<7 && driveTimes<900) {
		    			 data.setValidStatus(-1);
		    			 data.setLabelFlag("电动车/自行车");
		    		 }else   if(avgSpeed>41){
		    			 data.setValidStatus(-1);
		    			 data.setLabelFlag("高铁");
		    		 }else {
		    			 data.setValidStatus(1);
		    			 data.setLabelFlag("小车");
		    		 }
	    		 }
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
//	    		 data.setOverspeedTimes(0);
	    		  //插入数据
	    		 dataService.insertEvents(data, eventList);
	    		 status.setInValid(0);
	    		 //更新任务数据
	    		 status.setScanStatus(1);
	    		 dataService.updateDataStatusTask(status);	
	    		 log.info("进入正常更新");
	    	  }else {
	    		  //更新数据未无效
	    		  log.info("进入无效");
	    		  status.setScanStatus(1);
	    		  status.setInValid(1);
		    	  dataService.updateDataStatusTask(status);
		    	  log.info("**************************无效数据空list");
	    	  }
			}catch(Exception e){
				e.printStackTrace();
				//有异常，进行回滚？
				 log.info("进入异常");
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
	
public static void main(String []args) {
	 String day1="2018-01-12 12:00:00.666".substring(0,10).replaceAll("-", "");
	 String day2=DateTools.getYYYYMMDD();
	 if(Integer.valueOf(day2).intValue()!=Integer.valueOf(day1)) {
		System.out.println("隔天数据");
	 }
}
}
