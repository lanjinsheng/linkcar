package com.idata365.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.service.DriveDataService;

@RestController
public class DriveSynController {
	@Autowired
    DriveDataService driveDataService;
    @RequestMapping(value = "/recieveDrive",method = RequestMethod.POST)
    boolean recieveDrive(@RequestBody  (required = false) List<Map<String,Object>> postList) {
    	if(postList==null || postList.size()==0)
    		return false;
    	try {
	    	for(Map<String,Object> map:postList) {
	    		Map<String,Object> main=(Map<String,Object>)map.get("drive");
	    		List<Map<String,Object>> events=(List<Map<String,Object>> )map.get("events");
	    		driveDataService.insertEvents(main, events);
	    	}
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    	return true;
    }
}
