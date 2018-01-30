package com.idata365.app.controller.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.ImNotify;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.ImService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class ImController extends BaseController {

	@Autowired
	ImService imService;
	@Autowired
	FamilyService familyService;
	@RequestMapping("/im/getMain")
    public Map<String,Object> getMain(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msgId=requestBodyParams.get("msgId");
	  	 if(ValidTools.isBlank(family) || ValidTools.isBlank(msgId)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=this.getUserId();
		return imService.getImMain(Long.valueOf(family.toString()), userId,Long.valueOf(msgId.toString()));
	}
	@RequestMapping("/im/getNotify")
    public Map<String,Object> getNotify(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
	  	 if(ValidTools.isBlank(family)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=this.getUserId();
		return imService.getImNotify(Long.valueOf(family.toString()), userId);
	}
    
	@RequestMapping("/im/getMsgs")
    public Map<String,Object> getMsgs(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msgId=requestBodyParams.get("msgId");
	  	 if(ValidTools.isBlank(family) || ValidTools.isBlank(msgId)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=this.getUserId();
		return imService.getImMsgs(Long.valueOf(family.toString()), userId,Long.valueOf(msgId.toString()));
	}
	@RequestMapping("/im/submitNotify")
    public Map<String,Object> submitNotify(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msg=requestBodyParams.get("msg");
	  	if(ValidTools.isBlank(family) || ValidTools.isBlank(msg)) {
    		return ResultUtils.rtFailParam(null);
	  	}
		UserInfo user=this.getUserInfo();
		ImNotify notify=new ImNotify();
		notify.setFamilyId(Long.valueOf(family.toString()));
		Map<String,Object> familyInfo=familyService.findFamilyByFamilyId(notify.getFamilyId());
		notify.setFamilyName(String.valueOf(familyInfo.get("familyName")));
		notify.setLeaderName(user.getNickName());
		notify.setLeaderId(user.getId());
		notify.setInUse(1);
		int rt= imService.insertNotify(notify);
		return ResultUtils.rtSuccess(null);
	}	
	
	
	@RequestMapping("/im/submitMsg")
    public Map<String,Object> submitMsg(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msg=requestBodyParams.get("msg");
	  	if(ValidTools.isBlank(family) || ValidTools.isBlank(msg)) {
    		return ResultUtils.rtFailParam(null);
	  	}
		UserInfo user=this.getUserInfo();
		ImMsg imMsg=new ImMsg();
		imMsg.setToUserId(user.getId());
		imMsg.setFromUserId(user.getId());
		imMsg.setMsg(msg.toString());
		imMsg.setIsRead(0);
		imMsg.setFamilyId(Long.valueOf(family.toString()));
		int rt= imService.insertMsg(imMsg);
		return ResultUtils.rtSuccess(null);
	}	
	
}
