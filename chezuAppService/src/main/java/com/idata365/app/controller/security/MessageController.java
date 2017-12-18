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
import com.idata365.app.util.ValidTools;


@RestController
public class MessageController  extends BaseController {

	@Autowired
	private MessageService messageService;
	public MessageController() {
	}

   /**
    * 
       * @Title: getMsgMainTypes
       * @Description: TODO(获取消息大类列表)
       * @param @param allRequestParams
       * @param @param requestBodyParams
       * @param @return    参数
       * @return Map<String,Object>    返回类型
       * @throws
       * @author LanYeYe
    */
    @RequestMapping("/msg/getMsgMainTypes")
    public Map<String,Object> getMsgMainTypes(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	rtMap.put("msgTypes", messageService.getMsgMainTypes(this.getUserId()));
    	return ResultUtils.rtSuccess(rtMap);
    }
    /**
     * 
        * @Title: getMsgListByType
        * @Description: TODO(获取某类消息列表)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/msg/getMsgListByType")
    public Map<String,Object> getMsgListByType(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("msgType")) || ValidTools.isBlank(requestBodyParams.get("startMsgId"))
        		||	ValidTools.isBlank(requestBodyParams.get("readStatus"))	
        			)
              return ResultUtils.rtFailParam(null);
    	
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	List<Map<String,Object>> list=messageService.getMsgListByType(requestBodyParams);
    	this.dealListObect2String(list);
    	rtMap.put("msgList", list);
    	return ResultUtils.rtSuccess(rtMap);
    }
    /**
     * 
        * @Title: hadRead
        * @Description: TODO(消息已读置位)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/msg/hadRead")
    public Map<String,Object> hadRead(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("msgId")))	
              return ResultUtils.rtFailParam(null);
    	
    	Long msgId=Long.valueOf(requestBodyParams.get("msgId").toString());
        messageService.updateRead(msgId);
    	return ResultUtils.rtSuccess(null);
    }
     
}