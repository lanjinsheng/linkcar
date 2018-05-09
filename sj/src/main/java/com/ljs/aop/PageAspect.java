package com.ljs.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.ljs.pojo.PageParameter;
import com.ljs.util.ServerUtil;
import com.ljs.util.Utility;

@Component
@Aspect
public class PageAspect {

	@Around("execution (* com.ljs.control*..*.*Page*(..)) && args(request)")
	public String afterPageAspect(ProceedingJoinPoint jp,
			HttpServletRequest request) {
		int page = request.getParameter("page") == null ? 1 : Integer
				.parseInt(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 10 : Integer
				.parseInt(request.getParameter("rows"));
		Map<String, Object> map = new HashMap<String, Object>();
		PageParameter p = new PageParameter();
		p.setCurrentPage(page);
		p.setPageSize(pageSize);
		map.put("pageObject", p);
		request.setAttribute("pageMap", map);
		StringBuffer sb = new StringBuffer("");
		String s;
		try {
			s = (String) jp.proceed();
			sb.append("{\"total\":" + p.getTotalCount() + ",");

			sb.append("\"rows\":");
			sb.append(s);
			if(map.get(Utility.RETURN_KEY)!=null)
			{
				 if( map.get(Utility.RETURN_KEY).equals(Utility.RETURN_OK)){
					 sb.append(",").append(Utility.rtJson(Utility.RETURN_OK,"操作成功",null).substring(1));
				 }
				 else if(map.get(Utility.RETURN_KEY).equals(Utility.RETURN_ERROR)){
					 sb.append(",").append(Utility.rtJson(Utility.RETURN_OK,"操作异常",null).substring(1));
				 }
				 else{
					 sb.append(",").append(Utility.rtJson(Utility.RETURN_OK,"操作成功",null).substring(1));	 
				 }
			}else if (map.get(ServerUtil.RETURN_KEY) != null) {
				if (Integer.parseInt(String.valueOf(map
						.get(ServerUtil.RETURN_KEY))) == ServerUtil.RETURN_OK) {
					sb.append(",").append(
							ServerUtil.rtJson(ServerUtil.RETURN_OK, "操作成功",
									null).substring(1));
				} else if (Integer.parseInt(String.valueOf(map
						.get(ServerUtil.RETURN_KEY))) == ServerUtil.RETURN_ERROR) {
					sb.append(",").append(
							ServerUtil.rtJson(ServerUtil.RETURN_ERROR, "操作异常",
									null).substring(1));
				} else {
					sb.append(",").append(
							ServerUtil.rtJson(ServerUtil.RETURN_OK, "操作成功",
									null).substring(1));
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
