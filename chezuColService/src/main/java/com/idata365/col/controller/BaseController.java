package com.idata365.col.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class BaseController<T> {
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPagerMap(Map<String,Object> param) {
		 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	  	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
		Map<String, Object>  pageMap= (Map<String, Object>) request.getAttribute("pageMap");
		if(param!=null)
			pageMap.putAll(param);
		return pageMap;
	}
}
