package com.idata365.app.controller.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.Message;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;


@RestController
public class MessageController  extends BaseController {

	@Autowired
	private MessageService messageService;
	public MessageController() {
	}

    @RequestMapping("/msg/sendRegMsg")
    public Map<String,Object> sendRegMsg(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	if(allRequestParams==null ||  ValidTools.isBlank(allRequestParams.get("key")))
            return ResultUtils.rtFailParam(null);
    	String key=allRequestParams.get("key");
    	long now=System.currentTimeMillis()-(5*60*1000);
    	try {
			long eventTime=Long.valueOf(SignUtils.decryptDataAes(key));
			if(eventTime<(now)) {//超时失效
				 return ResultUtils.rtFailParam(null,"链接地址已经失效了");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return ResultUtils.rtFail(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return ResultUtils.rtFail(null);
		}
        Long userId=this.getUserId();
        Message message=messageService.buildMessage(0L, "", "",userId, null, MessageEnum.SYSTEM_REG);
		//插入消息
  		messageService.insertMessage(message, MessageEnum.SYSTEM_REG);
  		//推送消息 极光还没绑定设备，此时不宜推送
         messageService.pushMessage(message,MessageEnum.SYSTEM_REG);
    	return ResultUtils.rtSuccess(null);
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
    	requestBodyParams.put("toUserId", this.getUserId());
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