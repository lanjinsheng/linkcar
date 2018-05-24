package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.DicService;
import com.idata365.app.util.ResultUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class NotifyController extends BaseController {
	private static long lastNotifyTime=0;
	private static String notifyText="";
	protected static final Logger LOG = LoggerFactory.getLogger(NotifyController.class);
	@Autowired
	DicService dicService;
	@RequestMapping("/indexNotice")
	public Map<String, Object> getIndexFightInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
			long now=System.currentTimeMillis();
			if((now-lastNotifyTime)>(3600*1000)){//一个小时重新去库获取
				notifyText=dicService.getNotify();
				lastNotifyTime=now;
			}
			Map<String,String> rtMap=new HashMap<String,String> ();
			rtMap.put("notice", notifyText);
		     return ResultUtils.rtSuccess(rtMap);
	}
    
 
}
