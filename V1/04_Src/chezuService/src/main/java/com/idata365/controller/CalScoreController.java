package com.idata365.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.entity.DriveDataMain;
import com.idata365.entity.DriveScore;
import com.idata365.service.CalScoreService;
import com.idata365.service.DriveMainColService;
import com.idata365.util.ResultUtils;


@RestController
public class CalScoreController extends BaseController<CalScoreController> {
	private final static Logger LOG = LoggerFactory.getLogger(CalScoreController.class);
	@Autowired  
	CalScoreService calScoreService; 
	@Autowired  
	DriveMainColService driveMainColService;
	   @RequestMapping(value = "/mng/getScoreByUH",method = RequestMethod.POST)
	    Map<String,Object> getScoreByUH(@RequestParam Long userId,@RequestParam Long habitId,@RequestParam String sign) {
//		      Long userId=Long.valueOf(param.get("userId").toString());
//		      Long habitId=Long.valueOf(param.get("habitId").toString());
//		      String sign=param.get("sign").toString();
		      boolean s=com.idata365.util.SignUtils.security(""+userId+habitId, sign);
		      if(!s) {
		    	  return ResultUtils.rtFailVerification(null);
		      }
		      //先从数据库缓存获取,如果无数据，则进行重新计算。
		      List<DriveScore> dsList=calScoreService.getDriveScoreByUH(userId, habitId);
		      if(dsList==null || dsList.size()==0) {
		    	  DriveDataMain data=driveMainColService.getDriveMain(userId, habitId);
		    	  Map<String,Object> rtMap=calScoreService.calScoreByUH(userId, habitId,data);
		    	  return ResultUtils.rtSuccess(rtMap);
		      }
		   	  LOG.info(userId+"======"+habitId);
	    	  return ResultUtils.rtSuccess(dsList);
		  
	    } 
}