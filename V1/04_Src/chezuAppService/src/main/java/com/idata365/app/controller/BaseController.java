package com.idata365.app.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


abstract  class BaseController {
	
	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	 
	protected String getFamilyInviteBasePath(String host) {
	   	     return host+"share/invite.html?key=";

	  }
	protected String getRegSendMsgUrl() {
		  RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	   	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
	   	     return "http://"+request.getServerName()+":"+request.getServerPort()+"/msg/sendRegMsg?key=";

	}
}
