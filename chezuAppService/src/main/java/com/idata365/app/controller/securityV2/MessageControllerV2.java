package com.idata365.app.controller.securityV2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.MessageServiceV2;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class MessageControllerV2 extends BaseController {

	@Autowired
	private MessageServiceV2 messageServiceV2;

	public MessageControllerV2() {
	}

    /**
     * 
        * @Title: deleteMessage
        * @Description: TODO(删除消息--逻辑删除)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author Lcc
     */
	@RequestMapping("/deleteMessage")
	public Map<String, Object> deleteMessage(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("msgIds")))
			return ResultUtils.rtFailParam(null);
		String msgIds = requestBodyParams.get("msgIds").toString();
		messageServiceV2.deleteMessage(msgIds);
		return ResultUtils.rtSuccess(null);
	}
     
}