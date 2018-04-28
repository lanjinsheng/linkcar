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
import com.idata365.app.remote.ChezuService;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.ResultUtils;

/**
 * Created by **** 
 */
@Component
@Aspect
public class WebControllerOpenAop {
	private final static Logger LOG = LoggerFactory.getLogger(WebControllerOpenAop.class);

public WebControllerOpenAop() {
	LOG.info("WebControllerAop init");
}
    //匹配com.idata365.col.controller包及其子包下的所有类的所有方法
   @Pointcut("execution(* com.idata365.app.controller.open..*.*(..))")
    public Object executeService(){
		return "ok";

    }

    @Around("executeService()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
    	 LOG.info("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());
         try {//obj之前可以写目标方法执行前的逻辑
             Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
             return obj;
         } catch (Throwable throwable) {
             throwable.printStackTrace();
         }
         return null;
    }
  
}

