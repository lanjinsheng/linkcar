package com.idata365.app.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

