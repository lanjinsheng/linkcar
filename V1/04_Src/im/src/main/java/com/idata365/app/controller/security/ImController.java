package com.idata365.app.controller.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.ImService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;

@RestController
public class ImController extends BaseController{
	@Autowired
    ImService	imService;
	@RequestMapping("/sendGlobalMessage")
	public Map<String, Object> sendGlobalMessage(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		long userId = this.getUserId();
		requestBodyParams.put("userId", userId);
		requestBodyParams.put("nick", this.getUserInfo().getNickName());
		requestBodyParams.put("time", DateTools.getCurDate2());
		imService.insertMsg(requestBodyParams);
		imService.sendGloadIm(requestBodyParams);
		return ResultUtils.rtSuccess(null);
	} 
	
	@RequestMapping("/getGlobalMessage")
	public  Map<String, Object> getGlobalMessage(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		Map<String,Object> rtMap=new HashMap<String,Object>();
		List<Map<String, String>> list=imService.getMsgs();
		rtMap.put("msgs", list);
		return ResultUtils.rtSuccess(rtMap);
	} 
	
}
