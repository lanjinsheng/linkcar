package com.idata365.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.util.GsonUtils;
import com.idata365.util.HttpUtils;
import com.idata365.util.ResultUtils;

@RestController
public class YingyanBaiduController {
	   @RequestMapping(value = "/yingyan/addPoint",method = RequestMethod.POST)
	    Map<String,Object> addPoint(@RequestBody  (required = false) Map<String,String> point) {
	    	if(point==null || point.size()==0)
	    		return ResultUtils.rtFailParam(null);
	    	try {
		    String s= HttpUtils.postYingyanEntity(point);
		    return (Map<String,Object>)GsonUtils.fromJson(s);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return ResultUtils.rtFail(null);
	    	}
	    }
	   @RequestMapping(value = "/yingyan/addPointList",method = RequestMethod.POST)
	    Map<String,Object> addPointList(@RequestBody  (required = false) List<Map<String,String>> pointList) {
	    	if(pointList==null || pointList.size()==0)
	    		return ResultUtils.rtFailParam(null);
	    	try {
		    String s= HttpUtils.postYingyanEntityList(pointList);
		    return (Map<String,Object>)GsonUtils.fromJson(s);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return ResultUtils.rtFail(null);
	    	}
	    }
	   
	   @RequestMapping(value = "/yingyan/analysis",method = RequestMethod.POST)
	    Map<String,Object> analysis(@RequestBody  (required = false) Map<String,String> param) {
	    	if(param==null || param.size()==0)
	    		return ResultUtils.rtFailParam(null);
	    	try {
		    String s= HttpUtils.getYingyanAnalysis(param);
		    return (Map<String,Object>)GsonUtils.fromJson(s);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return ResultUtils.rtFail(null);
	    	}
	    }
}
