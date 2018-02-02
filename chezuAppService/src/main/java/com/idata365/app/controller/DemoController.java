package com.idata365.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.entity.ImMsg;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.remote.ChezuService;
import com.idata365.app.service.FamilyInviteService;
import com.idata365.app.service.ImService;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;


@RestController
public class DemoController extends BaseController {
	@Autowired
	FamilyInviteService familyInviteService;
	@Autowired
	ChezuService chezuService ;
	@Autowired
	ImService imService;
	
	@Autowired
	MessageService messageService;
	
	 @RequestMapping("/test/getInviteListTest")
	public Map<String,Object> getInviteListTest(){
		 List<FamilyInvite> list= familyInviteService.getFamilyInviteByPhone("15851750576");
		return ResultUtils.rtSuccess(null);
	}
	 
	 @RequestMapping("/test/insertInvite")
	public Map<String,Object> insertInvite(){
		FamilyInvite familyInvite=new FamilyInvite();
		familyInvite.setMemberUserId(10000L);
		 Long inviteId=familyInviteService.insertInviteFamily(familyInvite);
		 System.out.println(inviteId);
		 return ResultUtils.rtSuccess(null);
	}
	 
	@RequestMapping("/test/getRemoteGps")
	public Map<String,Object> getRemoteGps(){
		 String args=28+""+1514160360;
		 String sign=SignUtils.encryptHMAC(args);
		 Map<String,Object> map=new HashMap<String,Object> ();
		 map.put("userId", 28);
		 map.put("habitId", 1514160360);
		 map.put("sign", sign);
		 Map<String,Object> datas=chezuService.getGpsByUH(map);
		 return ResultUtils.rtSuccess(datas);
	}
	@RequestMapping("/im/getMsgsTest")
    public Map<String,Object> getMsgs(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msgId=requestBodyParams.get("msgId");
		Object isHistory=requestBodyParams.get("isHistory");
	  	 if(ValidTools.isBlank(family) || ValidTools.isBlank(msgId) || ValidTools.isBlank(isHistory)) {
    		 return ResultUtils.rtFailParam(null);
	  	 }
		long userId=80L;
		Map<String,Object> rtMap= imService.getImMsgs(Long.valueOf(family.toString()), userId,Long.valueOf(msgId.toString()),Integer.valueOf(isHistory.toString()),this.getImgBasePath());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	@RequestMapping("/test/messageAdd")
    public Map<String,Object> messageAdd(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Message msg=new Message(); 
		msg.setId(135L);
		messageService.pushMessage(msg, MessageEnum.SYSTEM_REG);
		return ResultUtils.rtSuccess(null);
	}
	
	
	@RequestMapping("/im/submitMsgTest")
    public Map<String,Object> submitMsg(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		Object family =requestBodyParams.get("familyId");
		Object msg=requestBodyParams.get("msg");
	  	if(ValidTools.isBlank(family) || ValidTools.isBlank(msg)) {
    		return ResultUtils.rtFailParam(null);
	  	}
		ImMsg imMsg=new ImMsg();
		imMsg.setToUserId(80L);
		imMsg.setFromUserId(80L);
		imMsg.setMsg(msg.toString());
		imMsg.setIsRead(0);
		imMsg.setFamilyId(Long.valueOf(family.toString()));
		imService.insertMsg(imMsg);
		return ResultUtils.rtSuccess(null);
	}	
}