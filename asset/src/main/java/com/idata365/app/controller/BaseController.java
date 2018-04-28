package com.idata365.app.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.idata365.app.config.SystemProperties;


abstract  class BaseController {
	@Autowired
	private SystemProperties systemProperties;
	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	 
	protected String getFamilyInviteBasePath(String host) {
	   	     return host+"share/invite.html?key=";

	  }
	protected String getRegSendMsgUrl() {
	   	     return systemProperties.getAppHost()+"msg/sendRegMsg?key=";

	}
	
	protected String getImgBasePath() {
	   	 return systemProperties.getAppHost()+"userFiles/getImgs?key=";
     
	  }
}
