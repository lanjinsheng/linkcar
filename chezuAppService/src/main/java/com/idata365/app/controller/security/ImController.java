package com.idata365.app.controller.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.ImNotify;
import com.idata365.app.entity.TaskImMessagePush;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.ImService;
import com.idata365.app.service.MessageService;
import com.idata365.app.service.TaskImMessagePushService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.StringTools;
import com.idata365.app.util.ValidTools;

@RestController
public class ImController extends BaseController {

	@Autowired
	ImService imService;
	@Autowired
	FamilyService familyService;
	@Autowired
	TaskImMessagePushService taskImMessagePushService;
	@Autowired
	MessageService messageService;
	@RequestMapping("/im/getMain")
    public Map<String,Object> getMain(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msgId=requestBodyParams.get("msgId");
	  	 if(ValidTools.isBlank(family) || ValidTools.isBlank(msgId)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=this.getUserId();
		return  ResultUtils.rtSuccess(imService.getImMain(Long.valueOf(family.toString()), userId,Long.valueOf(msgId.toString())));
	}
	@RequestMapping("/im/getNotify")
    public Map<String,Object> getNotify(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
	  	 if(ValidTools.isBlank(family)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=this.getUserId();
		return ResultUtils.rtSuccess(imService.getImNotify(Long.valueOf(family.toString()), userId,this.getImgBasePath()));
	}
    
	@RequestMapping("/im/getMsgs")
    public Map<String,Object> getMsgs(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msgId=requestBodyParams.get("msgId");
		Object isHistory=requestBodyParams.get("isHistory");
	  	 if(ValidTools.isBlank(family) || ValidTools.isBlank(msgId) || ValidTools.isBlank(isHistory)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=this.getUserId();
		Map<String,Object> rtMap= imService.getImMsgs(Long.valueOf(family.toString()), userId,Long.valueOf(msgId.toString()),Integer.valueOf(isHistory.toString()),this.getImgBasePath());
		return ResultUtils.rtSuccess(rtMap);
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
		
		if(user.getNickName()==null) {
			notify.setLeaderName(StringTools.getPhoneHidden(user.getPhone()));
		}else {
			notify.setLeaderName(user.getNickName());
		}
		notify.setLeaderId(user.getId());
		notify.setLeaderPic(user.getImgUrl());
		notify.setInUse(1);
		notify.setFamilyId(Long.valueOf(family.toString()));
		notify.setNotifyMsg(msg.toString());
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
		Object users=requestBodyParams.get("users");
		imMsg.setAtUsers(users!=null?String.valueOf(users):null);
		int rt= imService.insertMsg(imMsg);
		if(users!=null && !users.equals("")) {
				String []arrayUsers=String.valueOf(users).split(",");
				//直接发送
				for(String userId:arrayUsers) {
					messageService.pushImMessageByTask(imMsg,userId);
				}
			    //暂时不从任务走,下面只插入任务，但不执行任务
				TaskImMessagePush task=new TaskImMessagePush();
				task.setImMessageId(imMsg.getId());
				taskImMessagePushService.insertTaskImMessagePush(task);
			
		}
		return ResultUtils.rtSuccess(null);
	}	
	
}
