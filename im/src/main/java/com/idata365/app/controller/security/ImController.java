package com.idata365.app.controller.security;

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
		if(requestBodyParams.get("familyId").equals("")) {
			requestBodyParams.put("familyId", 0L);
		}
		imService.insertMsg(requestBodyParams);
		requestBodyParams.put("imgUrl", this.getImgBasePath()+ this.getUserInfo().getImgUrl());
		if(Integer.valueOf(requestBodyParams.get("type").toString())==0) {
			imService.sendGloadIm(requestBodyParams);
		}else if(Integer.valueOf(requestBodyParams.get("type").toString())==1 || Integer.valueOf(requestBodyParams.get("type").toString())==2) {
			imService.sendUserFamilyIm(requestBodyParams,String.valueOf(userId),Integer.valueOf(requestBodyParams.get("type").toString()));
		}
		return ResultUtils.rtSuccess(null);
	} 
	
	@RequestMapping("/getGlobalMessage")
	public  Map<String, Object> getGlobalMessage(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
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
		Map<String,List<Map<String,String>>> rtMap=imService.getMsgs(this.getImgBasePath(),myFamily,partakeFamily);
		return ResultUtils.rtSuccess(rtMap);
	} 
	
	
	@RequestMapping("/getFamiliesUsers")
	public  Map<String, Object> getFamiliesUsers(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		String sign=SignUtils.encryptHMAC(String.valueOf(this.getUserId()));
		Map<String, List<Map<String,Object>>> rtMap=chezuAppService.familyUsers(this.getUserId(), sign);
		return ResultUtils.rtSuccess(rtMap);
	} 
}
