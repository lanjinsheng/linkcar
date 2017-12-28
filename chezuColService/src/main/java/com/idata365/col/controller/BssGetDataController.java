package com.idata365.col.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.col.api.QQSSOTools;
import com.idata365.col.api.SSOTools;
import com.idata365.col.config.SystemProperties;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.SensorDataLog;
import com.idata365.col.entity.UploadDataStatus;
import com.idata365.col.service.DataService;
import com.idata365.col.util.ExcelUtils;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.PhoneGpsUtil;


@RestController
public class BssGetDataController extends BaseController<BssGetDataController> {
	private final static Logger LOG = LoggerFactory.getLogger(BssGetDataController.class);
    @Autowired
    DataService dataService;
	@Autowired  
	SystemProperties systemProPerties; 
    
    @RequestMapping(value = "/v1/viewDriveData",method = RequestMethod.POST)
    public Map<String,Object>  viewDriveData(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) throws IOException {
    	return null;
    } 
    @RequestMapping(value = "/v1/listPageDriveLog",produces = "application/json;charset=UTF-8")
    public String listPageDriveLog(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	    LOG.info("start");
    	    Map<String,Object> pageMap=this.getPagerMap(allRequestParams);
        	String s=dataService.listPageDriveLog(pageMap);
        	LOG.info("end");
        	return s;
    }
    @RequestMapping(value = "/v1/listPageDriveLogTest",produces = "application/json;charset=UTF-8")
    public String listPageDriveLogTest(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	    LOG.info("start");
    	    Map<String,Object> pageMap=this.getPagerMap(allRequestParams);
        	String s=dataService.listPageDriveLogTest(pageMap);
        	LOG.info("end");
        	return s;
    } 
    
    @RequestMapping(value = "/v1/downLoadDrive",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadDriveData(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
		  long id=Long.valueOf(allRequestParams.get("id").toString());
		  long uid=System.currentTimeMillis();
		  DriveDataLog driveLog=  dataService.getDriveLog(id);
		  List<DriveDataLog> drives=dataService.listDriveLogByUH(driveLog);
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "datas"+uid+".zip"));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
		   	 String tmpPath=systemProPerties.getFileTmpDir();
		     FileSystemResource   file = new FileSystemResource(tmpPath+id+System.currentTimeMillis());
	         ZipOutputStream  os= null;
	         try {
	        	 os= new ZipOutputStream(file.getOutputStream());
		         for(DriveDataLog drive:drives) {
				   	 String name="drive"+drive.getSeq();
				   	 if(drive.getFilePath().endsWith("_Q")) {
				   		 QQSSOTools.getSSOFile(drive.getFilePath(),os,name);
				   	 }else {
				   		 SSOTools.getSSOFile(drive.getFilePath(),os,name);
				   	 }
			   	 }
			   	 if(driveLog.getHadSensorData()==1) {
					  List<SensorDataLog> sensors=dataService.listSensorByUH(driveLog);
					  for(SensorDataLog sensor:sensors) {
						   	 String name="sensor"+driveLog.getSeq();
						   	 if(sensor.getFilePath().endsWith("_Q")) {
						   		QQSSOTools.getSSOFile(sensor.getFilePath(),os,name);
						   	 }else {
						   		 SSOTools.getSSOFile(sensor.getFilePath(),os,name);
						   	 }
					  }
				  }
			   	 
		        return ResponseEntity  
		                .ok()  
		                .headers(headers)  
		                .contentLength(file.contentLength()) 
		                .contentType(MediaType.parseMediaType("application/octet-stream"))  
		                .body(new InputStreamResource(file.getInputStream())); 
			  }catch(Exception e) {
				  e.printStackTrace();
			  }finally{
		          try {
		        	  if(os!=null) {
		        	  os.close();
		        	  }
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		      }
	  return null;
    }
    
    @RequestMapping(value = "/v1/downLoadGpsExcel",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> downLoadGpsExcel(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
		  long id=Long.valueOf(allRequestParams.get("id").toString());
		  DriveDataLog driveLog=  dataService.getDriveLog(id);
		  List<DriveDataLog> drives=dataService.listDriveLogByUH(driveLog);
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "Gps.xls"));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
	         List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	         try {
		         for(DriveDataLog drive:drives) {
		        	  StringBuffer json=new StringBuffer();
		        	     if(drive.getFilePath().endsWith("_Q")) {
		        	    	 QQSSOTools.getSSOFile(json,drive.getFilePath());
		        	     }else {
		        	    	 SSOTools.getSSOFile(json,drive.getFilePath());
		        	     }
				         Map<String,Object> jMap=GsonUtils.fromJson(json.toString());
				         if(jMap.get("gpsInfos")!=null) {
				        	 list.addAll((List)jMap.get("gpsInfos"));
				         }
			   	 }
		        File excel=ExcelUtils.saveToExcelGps(list);
		        FileSystemResource   file = new FileSystemResource(excel);
		        return ResponseEntity  
		                .ok()  
		                .headers(headers)  
		                .contentLength(file.contentLength()) 
		                .contentType(MediaType.parseMediaType("application/octet-stream"))  
		                .body(new InputStreamResource(file.getInputStream())); 
			  }catch(Exception e) {
				  e.printStackTrace();
			  }finally{
				  
		      }
	  return null;
    }
    
    @RequestMapping(value = "/v1/downLoadSensorExcel",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> downLoadSensorExcel(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
		  long id=Long.valueOf(allRequestParams.get("id").toString());
		  DriveDataLog driveLog=  dataService.getDriveLog(id);
		  List<SensorDataLog> sensors=dataService.listSensorByUH(driveLog);
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "SensorOther.xls"));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
	         List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	         List<Map<String,Object>> deviceInfos=new ArrayList<Map<String,Object>>();
	         try {
	        	 for(SensorDataLog sensor:sensors) {
	        		  StringBuffer json=new StringBuffer();
	        		     if(sensor.getFilePath().endsWith("_Q")) {
	        		    	 QQSSOTools.getSSOFile(json,sensor.getFilePath());
	        		     }else {
	        		    	 SSOTools.getSSOFile(json,sensor.getFilePath());
	        		     }
				         Map<String,Object> jMap=GsonUtils.fromJson(json.toString());
				         if(jMap.get("sensorInfos")!=null) {
				        	 list.addAll((List)jMap.get("sensorInfos"));
				         }
				         if(jMap.get("deviceInfos")!=null) {
				        	 deviceInfos.addAll((List)jMap.get("deviceInfos"));
				         }
				         
			    }
		        File excel=ExcelUtils.saveToExcelSensor(list,deviceInfos);
		        FileSystemResource   file = new FileSystemResource(excel);
		        return ResponseEntity  
		                .ok()  
		                .headers(headers)  
		                .contentLength(file.contentLength()) 
		                .contentType(MediaType.parseMediaType("application/octet-stream"))  
		                .body(new InputStreamResource(file.getInputStream())); 
			  }catch(Exception e) {
				  e.printStackTrace();
			  }finally{
				  
		      }
	  return null;
    }
    
    @RequestMapping(value = "/v1/downLoadAlarmExcel",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> downLoadAlarmExcel(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
		  long id=Long.valueOf(allRequestParams.get("id").toString());
		  DriveDataLog driveLog=  dataService.getDriveLog(id);
		  List<DriveDataLog> drives=dataService.listDriveLogByUH(driveLog);
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "Alarm.xls"));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
	         List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	         try {
		    	  for(DriveDataLog drive:drives) {
		    		     StringBuffer json=new StringBuffer();
		    		     if(drive.getFilePath().endsWith("_Q")) {
				           QQSSOTools.getSSOFile(json,drive.getFilePath());
		    		     }else {
		    		    	 SSOTools.getSSOFile(json,drive.getFilePath()); 
		    		     }
				         Map<String,Object> jMap=GsonUtils.fromJson(json.toString());
				         if(jMap.get("gpsInfos")!=null) {
				        	 list.addAll((List)jMap.get("gpsInfos"));
				         }
				 }
		    	  List<Map<String,Object>> eventList=new ArrayList<Map<String,Object>>();
		    	  if(list.size()>0) {
		    		  Map<String, Object> datasMap= PhoneGpsUtil.getGpsValues(list);
		    		 List<Map<String,Object>> alarmListJia= (List<Map<String,Object>>)datasMap.get("alarmListJia");
		    		 List<Map<String,Object>> alarmListJian= (List<Map<String,Object>>)datasMap.get("alarmListJian");
		    		 List<Map<String,Object>> alarmListZhuan= (List<Map<String,Object>>)datasMap.get("alarmListZhuan");
		    		 eventList.addAll(alarmListJia);
		    		 eventList.addAll(alarmListJian);
		    		 eventList.addAll(alarmListZhuan);
		    	  }
		        File excel=ExcelUtils.saveToExcelAlarm(eventList);
		        FileSystemResource   file = new FileSystemResource(excel);
		        return ResponseEntity  
		                .ok()  
		                .headers(headers)  
		                .contentLength(file.contentLength()) 
		                .contentType(MediaType.parseMediaType("application/octet-stream"))  
		                .body(new InputStreamResource(file.getInputStream())); 
			  }catch(Exception e) {
				  e.printStackTrace();
			  }finally{
				  
		      }
	  return null;
    }
    
    
    //以下接口仅供调试使用
    @RequestMapping(value = "/v1/getDriveDemo")
    public List<UploadDataStatus> getDriveDemo(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.putAll(allRequestParams);
    	return dataService.getUploadDataDemo(map);
    }
    
    @RequestMapping(value = "/v1/getDriveGpsDemo")
    public List<Map<String,Object>> getDriveGpsDemo(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	  LOG.info("start");
		  DriveDataLog driveLog=  new DriveDataLog();
		  driveLog.setUserId(Long.valueOf(allRequestParams.get("userId")));
		  driveLog.setHabitId(Long.valueOf(allRequestParams.get("habitId")));
		  List<DriveDataLog> drives=dataService.listDriveLogByUH(driveLog);
	         List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	         try {
		         for(DriveDataLog drive:drives) {
		        	  StringBuffer json=new StringBuffer();
		        	     if(drive.getFilePath().endsWith("_Q")) {
		        	    	 
		        	     }else {
		        	    	 SSOTools.getSSOFile(json,drive.getFilePath());
		        	     }
				         Map<String,Object> jMap=GsonUtils.fromJson(json.toString());
				         if(jMap.get("gpsInfos")!=null) {
				        	 list.addAll((List)jMap.get("gpsInfos"));
				         }
			   	 }
			  }catch(Exception e) {
				  e.printStackTrace();
			  }finally{
				  
		      }
			return list;
    }
    //以上接口仅供调试使用
}