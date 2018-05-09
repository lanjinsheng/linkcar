package com.ljs.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ljs.util.ServerUtil;


@Component
@Aspect
public class RequestActionAop {
	private final static String publicKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDrqGPMSEs7HsdGB6MORHGb8OAnJRLX9vXEQM3wnKmz7iH2yNcPgFvZgHn2rSw/vHM9111OfoH86hBaJpJQ7D9fv2wjdOdGOCkBbbajxhHXJDIO1ChcssQLjQmPg+aLbSv3+nanUB9rEbb6Kgn5/aDEtxN9rk8NP6Kb/QsdaSh1awIDAQAB";
	private static Logger log = Logger.getLogger(RequestActionAop.class);

	// @Autowired
	// @Qualifier("chebaoUserService")
	// InfUserService chebaoUserService;
	public RequestActionAop() {
		System.out.println("RequestActionAop start~~~~~");
	}
//
//	@Around("execution (* com.chebao.phonewebapi.controller.PhoneDataMainControl.phoneDriveV2(..))")
//	public String methodsAspect(ProceedingJoinPoint jp) {
//		
//	}
	
//	@Around("execution (* com.ljs.control.DataViewControl.*(..))")
//	public String securityAspect(ProceedingJoinPoint jp) {
//		Object rtString = null;
//		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder
//				.getRequestAttributes())).getRequest();
//		             HttpSession  s=request.getSession();
//		            String pass=String.valueOf(s.getAttribute("userPass"));
//		            		if(pass.equals("wdttdzt")){
//		            			try {
//		            				rtString = jp.proceed();
//		            			} catch (Throwable e) {
//		            				// TODO Auto-generated catch block
//		            				e.printStackTrace();
//		            				return ServerUtil.rtJsonFailErroCode4(e.getMessage());
//		            			}
//		            		}else{
//		            			return ServerUtil.rtJsonFailErroCode10(null, "操，非法访问！");
//		            		}
//		            		return String.valueOf(rtString);
//	}
	

}
