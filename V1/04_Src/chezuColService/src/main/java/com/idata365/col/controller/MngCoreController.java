package com.idata365.col.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.col.api.SSOTools;
import com.idata365.col.config.SystemProperties;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.service.DataService;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.PhoneGpsUtil;
import com.idata365.col.util.ResultUtils;


@RestController
public class MngCoreController extends BaseController<MngCoreController> {
	private final static Logger LOG = LoggerFactory.getLogger(MngCoreController.class);
    @Autowired
    DataService dataService;
	@Autowired  
	SystemProperties systemProPerties; 
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