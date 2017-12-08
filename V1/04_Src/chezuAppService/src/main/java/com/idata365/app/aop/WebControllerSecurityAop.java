package com.idata365.app.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.ResultUtils;

/**
 * Created by **** 
 */
@Component
@Aspect
public class WebControllerSecurityAop {
	private final static Logger LOG = LoggerFactory.getLogger(WebControllerSecurityAop.class);
	@Autowired
	LoginRegService loginRegService;
public WebControllerSecurityAop() {
	LOG.info("WebControllerAop init");
}
    //匹配com.idata365.col.controller包及其子包下的所有类的所有方法
   @Pointcut("execution(* com.idata365.app.controller.security..*.*(..))")
    public Object executeService(){
		return "ok";

    }

    @Around("executeService()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
    	  LOG.info("我是需要token校验的环绕通知!!!");
    	   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    	    String token= request.getHeader("token");
    	   LOG.info("拦截到了" + proceedingJoinPoint.getSignature().getName() +"方法...");    
    	    Signature signature = proceedingJoinPoint.getSignature();    
    	    MethodSignature methodSignature = (MethodSignature)signature;    
    	    Method targetMethod = methodSignature.getMethod();  
    	    if(token==null) {
    	    	 if(targetMethod.getReturnType().getTypeName().equals("org.springframework.http.ResponseEntity")){
    	    	    	return ResponseEntity.ok().body(ResultUtils.rtFailTokenInvalid(null, "无权限访问，请先登入!"));
    	    	    }else {
    	    	    	return ResultUtils.rtFailTokenInvalid(null, "无权限访问，请先登入!");
    	    	    }
    	    	
    	    }
    	    UsersAccount account=loginRegService.validToken(token);
    	    if(account==null) {
    	    	 if(targetMethod.getReturnType().getTypeName().equals("org.springframework.http.ResponseEntity")){
    	    		 return ResponseEntity.ok().body(ResultUtils.rtFailTokenInvalid(null, "登入信息已经过期或者在别的设备已登入，请重新登入!"));
 	    	    }else {
 	    	    	return ResultUtils.rtFailTokenInvalid(null, "登入信息已经过期或者在别的设备已登入，请重新登入!");
 	    	    }
    	    	
    	    }else {
    	    	UserInfo userInfo=new UserInfo();
    	    	userInfo.setUserAccount(account);
    	    	request.setAttribute("userInfo", userInfo);
    	    	try {//obj之前可以写目标方法执行前的逻辑
    	            Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
    	            return obj;
    	        } catch (Throwable throwable) {
    	            throwable.printStackTrace();
    	        }
    	    	
    	    }
        return null;
    }
  
}

