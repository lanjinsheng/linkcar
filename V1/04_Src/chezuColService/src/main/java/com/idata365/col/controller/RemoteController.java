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
public class RemoteController extends BaseController<RemoteController> {
	private final static Logger LOG = LoggerFactory.getLogger(RemoteController.class);
    @Autowired
    DataService dataService;
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
	  @RequestMapping(value = "/v1/updateUserDevice",method = {RequestMethod.POST,RequestMethod.GET})
	    public boolean  updateUserDevice(@RequestParam Map<String,Object> map) {
	    	  if(map.get("userId")==null || map.get("deviceToken")==null)
	    		  return false;
	    	  return dataService.addDeviceUserInfo(String.valueOf(map.get("deviceToken")), Long.valueOf(map.get("userId").toString()));
	    }
	  
	  
}