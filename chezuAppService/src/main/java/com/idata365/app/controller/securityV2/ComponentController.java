package com.idata365.app.controller.securityV2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.ComponentService;
import com.idata365.app.util.ResultUtils;

@RestController
public class ComponentController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(ComponentController.class);
	@Autowired
	ComponentService componentService;
	@RequestMapping(value = "/getUserComponent")
	Map<String, Object> getUserComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Map<String,Object> rtMap=componentService.getUserComponent(this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	
	@RequestMapping(value = "/getFamilyComponent")
	Map<String, Object> getFamilyComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Map<String,Object> rtMap=componentService.getFamilyComponent(this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}
}
