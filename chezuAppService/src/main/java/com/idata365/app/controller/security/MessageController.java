package com.idata365.app.controller.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.MessageService;
import com.idata365.app.util.ResultUtils;


@RestController
public class MessageController  extends BaseController {

	@Autowired
	private MessageService messageService;
	public MessageController() {
	}

 
    @RequestMapping("/msg/getMsgMainTypes")
    public Map<String,Object> getMsgMainTypes(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	rtMap.put("msgTypes", messageService.getMsgMainTypes(this.getUserId()));
    	return ResultUtils.rtSuccess(rtMap);
    }
    
    @RequestMapping("/msg/getMsgListByType")
    public Map<String,Object> getMsgListByType(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	List<Map<String,Object>> list=messageService.getMsgListByType(requestBodyParams);
    	this.dealListObect2String(list);
    	rtMap.put("msgList", list);
    	return ResultUtils.rtSuccess(rtMap);
    }
    
    @RequestMapping("/msg/hadRead")
    public Map<String,Object> hadRead(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
        Long msgId=Long.valueOf(requestBodyParams.get("msgId").toString());
        messageService.updateRead(msgId);
    	return ResultUtils.rtSuccess(null);
    }
     
}