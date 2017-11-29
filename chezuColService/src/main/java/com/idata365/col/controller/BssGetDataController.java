package com.idata365.col.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.idata365.col.api.SSOTools;
import com.idata365.col.config.SystemProperties;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.SensorDataLog;
import com.idata365.col.service.DataService;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.PhoneGpsUtil;
import com.idata365.col.util.ResultUtils;


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
    @RequestMapping(value = "/v1/downLoadDrive",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadDriveData(@RequestParam (required = false) Map<String, Object> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
	
		  long id=Long.valueOf(allRequestParams.get("id").toString());
		  DriveDataLog driveLog=  dataService.getDriveLog(id);
		  List<DriveDataLog> drives=dataService.listDriveLogByUH(driveLog);
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "datas.zip"));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
		   	 String tmpPath=systemProPerties.getFileTmpDir();
		     FileSystemResource   file = new FileSystemResource(tmpPath+System.currentTimeMillis());
	         ZipOutputStream  os= null;
	         try {
	        	 os= new ZipOutputStream(file.getOutputStream());
		         for(DriveDataLog drive:drives) {
				   	 String name="drive"+System.currentTimeMillis();
			         SSOTools.getSSOFile(drive.getFilePath(),os,name);
			   	 }
			   	 if(driveLog.getHadSensorData()==1) {
					  List<SensorDataLog> sensors=dataService.listSensorByUH(driveLog);
					  for(SensorDataLog sensor:sensors) {
						   	 String name="sensor"+System.currentTimeMillis();
					         SSOTools.getSSOFile(sensor.getFilePath(),os,name);
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
    @RequestMapping(value = "/v1/getDriveResultByUH",method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object>  getDealResult(@RequestParam Map<String,Object> map) {
    	  DriveDataLog d=new DriveDataLog();	
    	  d.setUserId(Long.valueOf(map.get("userId").toString()));
    	  d.setHabitId(Long.valueOf(map.get("habitId").toString()));
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
    		  return ResultUtils.rtSuccess(datasMap);
    	  }
    	  return ResultUtils.rtSuccess(null);

    }
    @RequestMapping("/v1/index")
    public String index(){
        return "index";
    }
}