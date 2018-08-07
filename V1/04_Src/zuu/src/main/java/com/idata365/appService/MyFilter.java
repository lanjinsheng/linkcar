package com.idata365.appService;
import com.idata365.service.StatisticsService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
@Component
public class MyFilter extends ZuulFilter{
     @Autowired
	StatisticsService statisticsService;
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);
    public static Map<String,String> users=new HashMap<String,String>();
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token=request.getHeader("token");
        String pUserId="-1000";
        if(token!=null) {
        	if(users.get(token)!=null) {
        		pUserId=users.get(token);
        	}else {
        		pUserId=statisticsService.getUserId(token);
        		users.put(token, pUserId);
        	}
        }else {
        	token="-1";
        }
        MobileChbLogUtils.info(pUserId,request.getRequestURI(),token);
        return null;
    }
}