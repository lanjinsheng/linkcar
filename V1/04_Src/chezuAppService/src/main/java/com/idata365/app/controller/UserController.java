package com.idata365.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;


@RestController
public class UserController {

	@Autowired
	private LoginRegService loginRegService;
	public UserController() {
		System.out.println("UserController");
	}

    @RequestMapping("/account/loginTest")
    public Map<String,Object> loginTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	 String phone="15802187691";
    	 String password="14e1b600b1fd579f47433b88e8d85291";
    	String token="";
    		 UsersAccount account=new UsersAccount();
    		 String status=loginRegService.validAccount(phone, password,account);
    		if(status.equals(loginRegService.OK)) {//账号通过
    			token=UUID.randomUUID().toString().replaceAll("-", "");
    			loginRegService.insertToken(account.getId(),token);
    		}else {
    			
    		}
    	rtMap.put("status", status);
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    }
    @RequestMapping("/account/login")
    public Map<String,Object> login(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("verifyCode"))
    		||	ValidTools.isBlank(requestBodyParams.get("password"))	
    			)
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String verifyCode=String.valueOf(requestBodyParams.get("verifyCode"));
    	String password=String.valueOf(requestBodyParams.get("password"));
    	String  status=loginRegService.validVerifyCode(phone,2,verifyCode);
    	String token="";
    	if(status.equals(loginRegService.OK)) {//校验码通过
    		 UsersAccount account=new UsersAccount();
    		 status=loginRegService.validAccount(phone, password,account);
    		if(status.equals(loginRegService.OK)) {//账号通过
    			token=UUID.randomUUID().toString().replaceAll("-", "");
    			loginRegService.insertToken(account.getId(),token);
    		}else {
    			
    		}
    	}
    	else {
    	
    		
    	}
    	rtMap.put("status", status);
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    }
    @RequestMapping("/account/registerStep1")
    public Map<String,Object> registerStep1(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	if(requestBodyParams==null || ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("verifyCode")))
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String verifyCode=String.valueOf(requestBodyParams.get("verifyCode"));
    	String  status=loginRegService.validVerifyCode(phone,1,verifyCode);
    	if(status.equals(loginRegService.OK)) {//校验码通过
    		boolean isAccountExist=loginRegService.isPhoneExist(phone);
    		if(isAccountExist) {
    			return ResultUtils.rtFailParam(null, "账号已注册");
    		}else {
    			return ResultUtils.rtSuccess(null);
    		}
    	}
    	else {
    		if(status.equals(loginRegService.VC_ERR)) 
    		return ResultUtils.rtFailParam(null, "校验码无效");
    		if(status.equals(loginRegService.VC_EX)) 
    		return ResultUtils.rtFailParam(null, "校验码过期");
    	}
    	return ResultUtils.rtSuccess(null);
    }
    @RequestMapping("/account/registerStep2")
    public Map<String,Object> registerStep2(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("password")))
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String password=String.valueOf(requestBodyParams.get("password"));
    	String token=loginRegService.regUser(phone, password);
    	if(token==null) {
    		return ResultUtils.rtFailRequest(null);
    	}
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    }
    
    
    @RequestMapping("/account/findPasswordStep1")
    public Map<String,Object> findPasswordStep1(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	if(requestBodyParams==null || ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("verifyCode")))
            return ResultUtils.rtFailParam(null);
      	String phone=String.valueOf(requestBodyParams.get("phone"));
      	String verifyCode=String.valueOf(requestBodyParams.get("verifyCode"));
      	String  status=loginRegService.validVerifyCode(phone,3,verifyCode);
      	if(status.equals(loginRegService.OK)) {//校验码通过
      		boolean isAccountExist=loginRegService.isPhoneExist(phone);
    		if(isAccountExist) {
    			return ResultUtils.rtSuccess(null);
    		
    		}else {
    			return ResultUtils.rtFailParam(null, "账号不存在");
    		}
      	}
      	else {
      		if(status.equals(loginRegService.VC_ERR)) 
      		return ResultUtils.rtFailParam(null, "校验码无效");
      		if(status.equals(loginRegService.VC_EX)) 
      		return ResultUtils.rtFailParam(null, "校验码过期");
      	}
      	return ResultUtils.rtSuccess(null);
    }
    
    
    @RequestMapping("/account/findPasswordStep2")
    public Map<String,Object> findPasswordStep2(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("password")))
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String password=String.valueOf(requestBodyParams.get("password"));
    	String token=loginRegService.updateUserPwd(phone, password);
    	if(token==null) {
    		return ResultUtils.rtFailRequest(null);
    	}
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    } 
    
    
    
    @RequestMapping("/account/sendVerifyCode")
    public Map<String,Object> sendVerifyCode(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
   	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
         String sign=request.getHeader("sign");
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("codeType")))
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	boolean signValid=SignUtils.security(phone, sign);
    	if(!signValid) {
    		  return ResultUtils.rtFailVerification(null);
    	}
    	loginRegService.getVerifyCode(phone, Integer.valueOf(requestBodyParams.get("codeType").toString()));
    	return ResultUtils.rtSuccess(null);
    }
   
    
}