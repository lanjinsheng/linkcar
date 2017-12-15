package com.idata365.app.controller.security;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.entity.bean.UserInfo;

abstract  class BaseController {
	
	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	protected UserInfo getUserInfo() {
		   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	  	   HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
	  	   UserInfo userInfo=(UserInfo)  request.getAttribute("userInfo");
		   return userInfo;
	}
	protected Long getUserId() {
		   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	  	   HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
	  	   UserInfo userInfo=(UserInfo)  request.getAttribute("userInfo");
		   return userInfo.getId();
	}
}
