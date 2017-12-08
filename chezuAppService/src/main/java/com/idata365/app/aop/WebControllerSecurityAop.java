package com.idata365.app.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by **** 
 */
@Component
@Aspect
public class WebControllerSecurityAop {
	private final static Logger LOG = LoggerFactory.getLogger(WebControllerSecurityAop.class);
public WebControllerSecurityAop() {
	LOG.info("WebControllerAop init");
}
    //匹配com.idata365.col.controller包及其子包下的所有类的所有方法
   @Pointcut("execution(* com.idata365.app.controller.security..*.*(..))")
    public Object executeService(){
		return "ok";

    }

    /**
     * 前置通知，方法调用前被调用
     * @param joinPoint
     */
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        System.out.println("我是需要token校验的前置通知!!!");
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
  	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //获取目标方法的参数信息
//        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
       LOG.info(signature.getName());
        //AOP代理类的名字
       LOG.info(signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
    }

  
}

