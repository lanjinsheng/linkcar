package com.idata365.app.controller.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.controller.BaseController;
import com.idata365.app.util.ResultUtils;

 
@RestController("ImgsInfoSecurityController")
public class ImgsInfoController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(ImgsInfoController.class);
    @Autowired
	SystemProperties systemProperties;
	
 
    @RequestMapping("/userFiles/test")
    public Map<String,Object> test(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	LOG.info("userId:"+this.getUserId());
    	return ResultUtils.rtSuccess(rtMap);
    }
}
