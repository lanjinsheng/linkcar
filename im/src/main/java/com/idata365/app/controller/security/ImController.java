package com.idata365.app.controller.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.service.ImService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class ImController extends BaseController{
	@Autowired
    ImService	imService;
	@Autowired
	ChezuAppService chezuAppService;
	@RequestMapping("/sendGlobalMessage")
	public Map<String, Object> sendGlobalMessage(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		long userId = this.getUserId();
		requestBodyParams.put("userId", userId);
		requestBodyParams.put("nick", this.getUserInfo().getNickName());
		requestBodyParams.put("time", DateTools.getCurDate2());
		requestBodyParams.put("imgUrl", this.getUserInfo().getImgUrl());
		imService.insertMsg(requestBodyParams);
		if(requestBodyParams.get("type").equals("0")) {
			imService.sendGloadIm(requestBodyParams);
		}else if(requestBodyParams.get("type").equals("1") || requestBodyParams.get("type").equals("2")) {
			imService.sendUserFamilyIm(requestBodyParams,String.valueOf(userId));
		}
		return ResultUtils.rtSuccess(null);
	} 
	
	@RequestMapping("/getGlobalMessage")
	public  Map<String, Object> getGlobalMessage(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		Map<String,Object> rtMap=new HashMap<String,Object>();
		Long myFamily=0L;
		Long partakeFamily=0L;
		String sign=SignUtils.encryptHMAC(String.valueOf(this.getUserId()));
		String familyIds=chezuAppService.getFamiliesByUserId(this.getUserId(), sign);
		if(familyIds!=null) {
			if(familyIds.length()>0) {
				String []ids=familyIds.split(",");
				myFamily=Long.valueOf(ids[0]);
				partakeFamily=Long.valueOf(ids[1]);
			}
		}
		List<Map<String, String>> list=imService.getMsgs(this.getImgBasePath(),myFamily,partakeFamily);
		rtMap.put("msgs", list);
		return ResultUtils.rtSuccess(rtMap);
	} 
	
}
