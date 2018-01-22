package com.idata365.app.controller.security;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	protected String getImgBasePath() {
		  RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	   	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
	   	  return "http://"+request.getServerName()+":"+request.getServerPort()+"/userFiles/getImgs?key=";
       
	  }
	
	protected String getFamilyInviteBasePath() {
		  RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	   	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
	   	     return "http://"+request.getServerName()+":"+request.getServerPort()+"/share/goInvite?key=";

	  }
	protected String getFamilyInviteBasePath(String host) {
	   	     return host+"share/invite.html?key=";

	  }
	protected void dealListObect2String(List<Map<String,Object>> list) {
		for(Map<String,Object> m: list) {
			Set<String> keySet=m.keySet();
			Iterator<String> it=keySet.iterator();
			while(it.hasNext()) {
				String key=it.next();
				String value=m.get(key)==null?"":String.valueOf(m.get(key));
				m.put(key,value );
			}
		}
	}
}
