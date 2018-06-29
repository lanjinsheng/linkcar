package com.idata365.app.controller.security;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.bean.UserInfo;

public abstract  class BaseController {
	@Autowired
	private SystemProperties systemProperties;
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
	   	  return systemProperties.getAppHost()+"userFiles/getImgs?key=";
       
	  }
	
	protected String getFamilyInviteBasePath() {
	   	     return systemProperties.getAppHost()+"share/goInvite?key=";

	  }
	protected String getFamilyInviteBasePath(String host) {
	   	     return host+"share/invite1.html?key=";

	}
	protected String getFamilyShareBasePath(String host) {
  	     return host+"share/invite2.html?key=";

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
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPagerMap(HttpServletRequest request) {
		return (Map<String, Object>) request.getAttribute("pageMap");
	}
	/**
	 * 将request参数转换成Map
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> requestParameterToMap(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			m.put(name, request.getParameter(name).trim());
		}
		return m;
	}
}
