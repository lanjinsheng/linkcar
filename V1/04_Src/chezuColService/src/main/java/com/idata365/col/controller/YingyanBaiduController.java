package com.idata365.col.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.col.api.QQSSOTools;
import com.idata365.col.api.SSOTools;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.service.DataService;
import com.idata365.col.service.YingyanService;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.ResultUtils;

@RestController
public class YingyanBaiduController {
	private final static Logger LOG = LoggerFactory.getLogger(MngCoreController.class);
	    @Autowired
	    YingyanService yingyanService;
	    @Autowired
	    DataService dataService;
	   @RequestMapping(value = "/yingyan/addPointListByUH",method = RequestMethod.POST)
	    Map<String,Object> addPointList(@RequestBody  (required = false) Map<String,String> param) {
		   DriveDataLog d=new DriveDataLog();	
	    	  d.setUserId(Long.valueOf(param.get("userId").toString()));
	    	  d.setHabitId(Long.valueOf(param.get("habitId").toString()));
	    	  List<DriveDataLog> drives=dataService.listDriveLogByUH(d);
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
	    	  if(list.size()>0) {
	    		  yingyanService.dealList(list,param.get("userId").toString(),param.get("habitId").toString());
	    		  LOG.info(GsonUtils.toJson(list,true));
	    		  Map<String, Object> datasMap= yingyanService.addPointsList(list, param.get("userId"));
	    		  if(datasMap==null) {
	    			  return ResultUtils.rtFail(datasMap);
	    		  }
	    		  return ResultUtils.rtSuccess(datasMap);
	    	  }
	    	  return ResultUtils.rtSuccess(null);
	    }
	   
}
