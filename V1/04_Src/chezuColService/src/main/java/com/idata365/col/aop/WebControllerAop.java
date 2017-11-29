package com.idata365.col.aop;

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

/**
 * Created by **** 
 */
@Component
@Aspect
public class WebControllerAop {
	private final static Logger LOG = LoggerFactory.getLogger(WebControllerAop.class);
public WebControllerAop() {
	LOG.info("WebControllerAop init");
}
    //匹配com.idata365.col.controller包及其子包下的所有类的所有方法
   @Pointcut("execution(* com.idata365.col.controller..*.*(..))")
    public void executeService(){

    }

    /**
     * 前置通知，方法调用前被调用
     * @param joinPoint
     */
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        System.out.println("我是前置通知!!!");
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

    /**
     * 后置返回通知
     * 这里需要注意的是:
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(value = "execution(* com.idata365.col.controller..*.*(..))",returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint,Object keys){

    	 LOG.info("第一个后置返回通知的返回值："+keys);
    }

    @AfterReturning(value = "execution(* com.idata365.col.controller..*.*(..))",returning = "keys",argNames = "keys")
    public void doAfterReturningAdvice2(String keys){

    	 LOG.info("第二个后置返回通知的返回值："+keys);
    }

    /**
     * 后置异常通知
     *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "executeService()",throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){
        //目标方法名：
    	 LOG.info(joinPoint.getSignature().getName());
        if(exception instanceof NullPointerException){
        	 LOG.info("发生了空指针异常!!!!!");
        }
    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    @After("executeService()")
    public void doAfterAdvice(JoinPoint joinPoint){

    	 LOG.info("后置通知执行了!!!!");
    }

    /**
     * 环绕通知：
     *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around("execution(* com.idata365.col.controller..*.testAround*(..))")
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

