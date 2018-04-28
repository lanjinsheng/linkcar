package com.idata365.app.aop;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.entity.PageParameter;
import com.idata365.app.util.ResultUtils;



@Component
@Aspect
public class PageAspect
{
 
    @Around("execution (* com.idata365.app.controller..*.*Page*(..))")
    public String afterPageAspect(ProceedingJoinPoint  jp) 
    {
    	 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
     	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    	int page=request.getParameter("page")==null?1:Integer.parseInt(request.getParameter("page"));
    	int pageSize = request.getParameter("rows")==null?10:Integer.parseInt(request.getParameter("rows"));
    	Object[] args = jp.getArgs();  
    	if(args.length>1) {
    		Map<String,Object> m=(Map<String,Object>)args[1];
    		page=Integer.valueOf(m.get("page").toString());
    		pageSize=Integer.valueOf(m.get("rows").toString());
    	}
    	Map<String,Object> map = new HashMap<String,Object>();
    	PageParameter p=new PageParameter();
    	p.setCurrentPage(page);
    	p.setPageSize(pageSize);
    	map.put("pageObject", p);
    	request.setAttribute("pageMap", map);
    	StringBuffer sb = new StringBuffer("");
    	String s;
		try {
			s = (String) jp.proceed();
			sb.append("{\"total\":"+p.getTotalCount()+",");
			sb.append("\"rows\":");
			sb.append(s);
			if(map.get(ResultUtils.RETURN_KEY)!=null)
			{
				if(Integer.valueOf(map.get(ResultUtils.RETURN_KEY).toString())==ResultUtils.STATUS_SUCCESS){
					sb.append(",").append(ResultUtils.rtJson(ResultUtils.STATUS_SUCCESS,"操作成功",null).substring(1));
				}
				else{
					sb.append(",").append(ResultUtils.rtJson(ResultUtils.STATUS_FAIL,"操作异常",null).substring(1));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
			
    	return sb.toString();
    } 
    	  
    

}
