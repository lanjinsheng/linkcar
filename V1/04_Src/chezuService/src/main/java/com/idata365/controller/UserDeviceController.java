package com.idata365.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.service.UserDeviceService;


@RestController
public class UserDeviceController  {
	private final static Logger LOG = LoggerFactory.getLogger(UserDeviceController.class);
    @Autowired
    UserDeviceService userDeviceService;
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
	    	  return userDeviceService.addDeviceUserInfo(String.valueOf(map.get("deviceToken")), Long.valueOf(map.get("userId").toString()));
	    }
	  
	  
}