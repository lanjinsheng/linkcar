package com.idata365.app.controller.securityV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.DicService;
import com.idata365.app.util.ResultUtils;

@RestController
public class PlayHelperController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(PlayHelperController.class);
	@Autowired
	DicService dicService;
	private static long lastNotifyTime=0;
	List<Map<String, String>> result = new ArrayList<>();
	@RequestMapping("/playHelper")
	public Map<String, Object> playHelper(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		List<Map<String, String>> result = new ArrayList<>();
		long now=System.currentTimeMillis();
		if((now-lastNotifyTime)>(3600*1000)){//一个小时重新去库获取
			result=dicService.playHelper();
			lastNotifyTime=now;
		}
		return ResultUtils.rtSuccess(result);
	}

}
