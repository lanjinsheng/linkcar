package com.idata365.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.entity.DriveDataEvent;
import com.idata365.entity.DriveDataLog;
import com.idata365.entity.DriveScore;
import com.idata365.partnerApi.QQSSOTools;
import com.idata365.partnerApi.SSOTools;
import com.idata365.service.CalScoreService;
import com.idata365.service.DriveOpenService;
import com.idata365.util.GsonUtils;
import com.idata365.util.ResultUtils;


@RestController
public class DriveOpenController extends BaseController<DriveOpenController> {
	private final static Logger LOG = LoggerFactory.getLogger(DriveOpenController.class);
	@Autowired  
	DriveOpenService driveOpenService; 
	   @RequestMapping(value = "/drive/getGpsByUH",method = RequestMethod.POST)
	    Map<String,Object> getGpsByUH(@RequestParam Long userId,@RequestParam Long habitId,@RequestParam String sign) {
		      boolean s=com.idata365.util.SignUtils.security(""+userId+habitId, sign);
		      if(!s) {
		    	  return ResultUtils.rtFailVerification(null);
		      }
		      DriveDataLog d=new DriveDataLog();
		      d.setUserId(userId);
		      d.setHabitId(habitId);
		      List<DriveDataLog> drives=driveOpenService.listDriveLogByUH(d);
	    	  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
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
	    	  List<DriveDataEvent> events=driveOpenService.listDriveAlarmByUH(userId,habitId);
	    	  Map<String,Object> datas=new HashMap<String,Object>();
	    	  datas.put("gpsInfo", list);
	    	  datas.put("alarmInfo", events);
	    	  return datas;
	    } 
}