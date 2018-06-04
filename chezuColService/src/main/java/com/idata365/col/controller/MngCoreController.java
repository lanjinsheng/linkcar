package com.idata365.col.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.col.api.QQSSOTools;
import com.idata365.col.api.SSOTools;
import com.idata365.col.config.SystemProperties;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.service.DataService;
import com.idata365.col.service.YingyanService;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.OverspeedUtil;
import com.idata365.col.util.PhoneGpsUtil;
import com.idata365.col.util.ResultUtils;


@RestController
public class MngCoreController extends BaseController<MngCoreController> {
	private final static Logger LOG = Logger.getLogger(MngCoreController.class);
    @Autowired
    DataService dataService;
    @Autowired
    YingyanService yingyanService;
	@Autowired  
	SystemProperties systemProPerties; 
	
	/**
	 * 
	    * @Title: getDriveResultByUH
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param map
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	  @RequestMapping(value = "/v1/getDriveResultByUH",method = {RequestMethod.POST,RequestMethod.GET})
	    public Map<String,Object>  getDriveResultByUH(@RequestParam Map<String,Object> map) {
	    	  DriveDataLog d=new DriveDataLog();	
	    	  d.setUserId(Long.valueOf(map.get("userId").toString()));
	    	  d.setHabitId(Long.valueOf(map.get("habitId").toString()));
	    	  List<DriveDataLog> drives=dataService.listDriveLogByUH(d);
	    	  List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	    	  List<Map<String,Object>> jkList=new ArrayList<Map<String,Object>>();
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
			         try {
				         if(jMap.get("overspeedGPSInfo")!=null) {
				        	 jkList.addAll((List)jMap.get("overspeedGPSInfo"));
				         }
			         }catch(Exception e) {
			        	 LOG.error(e);
			        	 e.printStackTrace();
			         }
			 }
	    	  if(list.size()>0) {
	    		  Map<String, Object> datasMap= PhoneGpsUtil.getGpsValues(list,"userId="+map.get("userId")+"==habitId="+map.get("habitId"));
	    		  List<Map<String,Object>> alarmList=OverspeedUtil.dealOverSpeed(jkList);
	    		  datasMap.put("alarmListChao", alarmList);
	    		  return ResultUtils.rtSuccess(datasMap);
	    	  }
	    	  return ResultUtils.rtSuccess(null);

	    }
	  @RequestMapping(value = "/v1/getGpsValuesTest",method = {RequestMethod.POST,RequestMethod.GET})
	    public Map<String,Object>  getGpsValuesTest(@RequestParam Map<String,Object> map) {
	    	  DriveDataLog d=new DriveDataLog();	
	    	  d.setUserId(Long.valueOf(map.get("userId").toString()));
	    	  d.setHabitId(Long.valueOf(map.get("habitId").toString()));
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
	    		  List<Map<String, String>> datas= PhoneGpsUtil.dealOrderGps(list);
	    		  for(Map<String, String> m:datas) {
	    			  System.out.println(m.get("t")+"====="+m.get("s"));
	    		  }
	    	  }
	    	  return ResultUtils.rtSuccess(null);

	    }
	  
	  
	   /**
	    * 
	       * @Title: getGpsByUH
	       * @Description: TODO(高德超速接口)
	       * @param @param param
	       * @param @return    参数
	       * @return Map<String,Object>    返回类型
	       * @throws
	       * @author LanYeYe
	    */
	   @RequestMapping(value = "/gaode/getGpsByUH",method = RequestMethod.POST)
	    Map<String,Object> getGpsByUH(@RequestBody  (required = false) Map<String,String> param) {
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
	    	
	    	  List<Map<String,Object>> alarmList=yingyanService.dealListGaode(list);
	    	  return ResultUtils.rtSuccess(alarmList);
		  
	    } 
	   
	   @RequestMapping(value = "/mng/getScoreByUHMap",method = RequestMethod.POST)
	    Map<String,Object> getScoreByUHMap(@RequestBody Map<String,Object> param) {
		      Long userId=Long.valueOf(param.get("userId").toString());
		      Long habitId=Long.valueOf(param.get("habitId").toString());
		      String sign=param.get("sign").toString();
		      boolean s=com.idata365.col.util.SignUtils.security(""+userId+habitId, sign);
		      if(!s) {
		    	  return ResultUtils.rtFailVerification(param);
		      }
		   	  LOG.info(userId+"======"+habitId);
	    	  return ResultUtils.rtSuccess(null);
		  
	    } 
 
}