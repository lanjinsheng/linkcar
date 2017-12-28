package com.idata365.col.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.col.service.YingyanService;

@RestController
public class YingyanBaiduController {
	  @Autowired
	    YingyanService yingyanService;
	   @RequestMapping(value = "/yingyan/addPoint",method = RequestMethod.POST)
	    Map<String,Object> addPoint(@RequestBody  (required = false) Map<String,String> point) {
	    	return yingyanService.addPoint(point);
	    }
	   @RequestMapping(value = "/yingyan/addPointList",method = RequestMethod.POST)
	    Map<String,Object> addPointList(@RequestBody  (required = false) List<Map<String,String>> pointList) {
		   return yingyanService.addPointList(pointList);
	    }
	   
	   @RequestMapping(value = "/yingyan/analysis",method = RequestMethod.POST)
	    Map<String,Object> analysis(@RequestBody  (required = false) Map<String,String> param) {
		   return yingyanService.analysis(param);
	    }
	   
}
