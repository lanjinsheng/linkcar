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

import com.idata365.app.config.SystemProperties;
import com.idata365.app.constant.SystemConstant;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;


@RestController
public class UserController extends BaseController{

	@Autowired
	private LoginRegService loginRegService;
	@Autowired
	private SystemProperties systemProperties;
	public UserController() {
		System.out.println("UserController");
	}

 /**
  * 
     * @Title: login
     * @Description: TODO(登录)
     * @param @param allRequestParams
     * @param @param requestBodyParams
     * @param @return    参数
     * @return Map<String,Object>    返回类型
     * @throws
     * @author LanYeYe
  */
	@SuppressWarnings("unused")
	@RequestMapping("/account/login")
    public Map<String,Object> login(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone"))||	ValidTools.isBlank(requestBodyParams.get("password"))	)
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String password=String.valueOf(requestBodyParams.get("password"));
    	String status=LoginRegService.VC_ERR;
		// 送审时，去掉验证码。正常场景下，开启验证码（当开关为0时候，关闭验证码。开关为1时，开启验证码）
		if (SystemConstant.LOGINCODE_SWITCH == 0)
		{
			status = LoginRegService.OK;
		}
		else
		{
			if (ValidTools.isBlank(requestBodyParams.get("verifyCode")))
			{
				return ResultUtils.rtFailParam(null);
			}
			String verifyCode = String.valueOf(requestBodyParams.get("verifyCode"));
			if (verifyCode.equals(systemProperties.getNbcode()))
			{
				status = LoginRegService.OK;
			}
			else
			{
				status = loginRegService.validVerifyCode(phone, 2, verifyCode);
			}
		}
    	String token="";
    	if(status.equals(LoginRegService.OK)) {//校验码通过
    		 UsersAccount account=new UsersAccount();
    		 status=loginRegService.validAccount(phone, password,account);
    		if(status.equals(LoginRegService.OK)) {//账号通过
    			token=UUID.randomUUID().toString().replaceAll("-", "");
    			loginRegService.insertToken(account.getId(),token);
    			rtMap.put("userId", account.getId());
    		}else {
    			
    		}
    	}
    	else {
    	
    		
    	}
    	rtMap.put("status", status);
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    }
    /**
     * 
        * @Title: registerStep1
        * @Description: TODO(注册校验1)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/account/registerStep1")
    public Map<String,Object> registerStep1(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	if(requestBodyParams==null || ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("verifyCode")))
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String verifyCode=String.valueOf(requestBodyParams.get("verifyCode"));
    	String  status=LoginRegService.VC_ERR;
    	if(verifyCode.equals(systemProperties.getNbcode())) {//测试使用万能验证码
    		status=LoginRegService.OK;
    	}else {
    		status=loginRegService.validVerifyCode(phone,1,verifyCode);
    	}
    	if(status.equals(LoginRegService.OK)) {//校验码通过
    		boolean isAccountExist=loginRegService.isPhoneExist(phone);
    		if(isAccountExist) {
    			return ResultUtils.rtFailParam(null, "账号已注册");
    		}else {
    			return ResultUtils.rtSuccess(null);
    		}
    	}
    	else {
    		if(status.equals(LoginRegService.VC_ERR)) 
    		return ResultUtils.rtFailParam(null, "校验码无效");
    		if(status.equals(LoginRegService.VC_EX)) 
    		return ResultUtils.rtFailParam(null, "校验码过期");
    	}
    	return ResultUtils.rtSuccess(null);
    }
    /**
     * 
        * @Title: registerStep2
        * @Description: TODO(注册提交2)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/account/registerStep2")
    public Map<String,Object> registerStep2(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("password")))
          return ResultUtils.rtFailParam(null);
    	// 校验是否需要邀请码
    			if (SystemConstant.INVITECODE_SWITCH == 1)
    			{
    				if (ValidTools.isBlank(requestBodyParams.get("inviteCode")))
    				{
    					return ResultUtils.rtFailParam(null);
    				}
    				else if (!String.valueOf(requestBodyParams.get("inviteCode")).equals(SystemConstant.INVITE_CODE))
    				{
    			    		return ResultUtils.rtFailParam(null, "邀请码错误");
    				}
    			}
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String password=String.valueOf(requestBodyParams.get("password"));
    	String token=loginRegService.regUser(phone, password,rtMap);
    	if(token==null) {
    		return ResultUtils.rtFailRequest(null);
    	}
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    }
    
    /**
     * 
        * @Title: findPasswordStep1
        * @Description: TODO(找回密码校验1)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/account/findPasswordStep1")
    public Map<String,Object> findPasswordStep1(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	if(requestBodyParams==null || ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("verifyCode")))
            return ResultUtils.rtFailParam(null);
      	String phone=String.valueOf(requestBodyParams.get("phone"));
      	String verifyCode=String.valueOf(requestBodyParams.get("verifyCode"));
      	String  status=LoginRegService.VC_ERR;
    	if(verifyCode.equals(systemProperties.getNbcode())) {//测试万能验证码
    		status=LoginRegService.OK;
    	}else {
    		status=loginRegService.validVerifyCode(phone,3,verifyCode);
    	}
      	if(status.equals(LoginRegService.OK)) {//校验码通过
      		boolean isAccountExist=loginRegService.isPhoneExist(phone);
    		if(isAccountExist) {
    			return ResultUtils.rtSuccess(null);
    		}else {
    			return ResultUtils.rtFailParam(null, "账号不存在");
    		}
      	}
      	else {
      		if(status.equals(LoginRegService.VC_ERR)) 
      		return ResultUtils.rtFailParam(null, "校验码无效");
      		if(status.equals(LoginRegService.VC_EX)) 
      		return ResultUtils.rtFailParam(null, "校验码过期");
      	}
      	return ResultUtils.rtSuccess(null);
    }
    
    /**
     * 
        * @Title: findPasswordStep2
        * @Description: TODO(找回密码提交2)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/account/findPasswordStep2")
    public Map<String,Object> findPasswordStep2(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("phone")) || ValidTools.isBlank(requestBodyParams.get("password")))
          return ResultUtils.rtFailParam(null);
    	String phone=String.valueOf(requestBodyParams.get("phone"));
    	String password=String.valueOf(requestBodyParams.get("password"));
    	String token=loginRegService.updateUserPwd(phone, password,rtMap);
    	if(token==null) {
    		return ResultUtils.rtFailRequest(null);
    	}
    	rtMap.put("token", token);
    	return ResultUtils.rtSuccess(rtMap);
    } 
    
    /**
     * 
        * @Title: sendVerifyCode
        * @Description: TODO(发送校验码)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    
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
   
    /**
     * 
        * @Title: addDeviceUserInfo
        * @Description: TODO(增加设备信息)
        * @param @param allRequestParams
        * @param @param requestBodyParams
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/account/addDeviceUserInfo")
    public Map<String,Object> addDeviceUserInfo(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	if(requestBodyParams==null ||  ValidTools.isBlank(requestBodyParams.get("deviceToken")))
          return ResultUtils.rtFailParam(null);
    	String deviceInfo=String.valueOf(requestBodyParams.get("deviceToken"));
    	String eventType=String.valueOf(requestBodyParams.get("eventType"));
    	long userId=0;
    	if( ValidTools.isNotBlank(requestBodyParams.get("userId"))){
    		 userId=Long.valueOf(requestBodyParams.get("userId").toString());
    	}
    	String alias=loginRegService.addDeviceUserInfo(deviceInfo, userId);
    	if(alias==null) {
    		return ResultUtils.rtFail(null);
    	}
    	rtMap.put("alias", alias);
    	if(eventType.equals("1")) {//1为注册事件
    		String toUrl=this.getRegSendMsgUrl();
    		try {
				toUrl=toUrl+SignUtils.encryptDataAes(String.valueOf(System.currentTimeMillis()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		rtMap.put("toUrl", toUrl);
    	}else {
    		rtMap.put("toUrl", "");
    	}
    	return ResultUtils.rtSuccess(rtMap);
    }
    
}