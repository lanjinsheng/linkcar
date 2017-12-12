package com.idata365.app.controller.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.controller.BaseController;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;
@RestController
public class UserInfoController extends BaseController{
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LoginRegService loginRegService;
	/**
	 * 
	    * @Title: updateNickName
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param allRequestParams
	    * @param @param requestBodyParams
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	  @RequestMapping("/user/updateNickName")
	  public Map<String,Object> updateNickName(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		  Long userId=Long.valueOf(requestBodyParams.get("userId").toString());
		  String nickName=String.valueOf(requestBodyParams.get("userId").toString());
		  userInfoService.updateNickName(userId, nickName);
		  return ResultUtils.rtSuccess(null);
	  }
	  
	  @RequestMapping("/user/getUserInfo")	  
	  public Map<String,Object> getUserInfo(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
//		  name	姓名	String	
//		  gender	性别	String	男：M；女：F
//		  nation	国籍	String	中国：C；其他：B
//		  driveCardType	驾驶证	String	C1;C2;C3;C4;B1;B2;A1;A2;A3
//		  birthday	生日	String	1990-09-04
//		  virginDay	初次领证日期	String	19900904
//		  validDay	有效起始日期	String	19900904
//		  validYears	有效年限	int	
//		  plateNo	车牌	String	
//		  cardTypeDesc	汽车类型	String	
//		  userTypeDesc	营运类型	String	
//		  modelTypeDesc	车辆类型	String	
//		  vin	vin码	String	
//		  engineNo	发动机号	String	
//		  frontDrivingImg	驾驶证正面	String	
//		  backDrivingImg	驾驶证背面	String	
//		  frontTravelImg	行驶证正面	String	
//		  backTravelImg	行驶证背面	String	
		  
		  Long userId=Long.valueOf(requestBodyParams.get("userId").toString());
		  getImgBasePath();
		  return ResultUtils.rtSuccess(null);
	  }
	  
	    @RequestMapping("uploadHeadImg")
	    public Map<String,Object>  uploadHeadImg(@RequestParam CommonsMultipartFile file,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) throws IOException {
	    	Long userId=this.getUserId();
	    	Map<String,String> rtMap=new HashMap<String,String>();
	    	try {
	               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
	    		   String key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.HEADER);
	               InputStream is=file.getInputStream();
	               SSOTools.saveOSS(is,key);
	               rtMap.put("imgUrl", getImgBasePath()+key);
	               is.close();
	               userInfoService.updateImgUrl(key, userId);
	           }catch (Exception e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	               return ResultUtils.rtFail(null); 
	           }
	       return ResultUtils.rtSuccess(rtMap);
	    }
	    /**
	     * 
	        * @Title: modifyPhoneStep1
	        * @Description: TODO(验证手机是否可以注册)
	        * @param @param file
	        * @param @param requestBodyParams
	        * @param @return
	        * @param @throws IOException    参数
	        * @return Map<String,Object>    返回类型
	        * @throws
	        * @author LanYeYe
	     */
	    @RequestMapping("modifyPhoneStep1")
	    public Map<String,Object>  modifyPhoneStep1(@RequestParam CommonsMultipartFile file,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
	    	 RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	   	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
	   	     String sign=request.getHeader("sign");
	    	 Long userId=this.getUserId();
	    	 if(userId==null || ValidTools.isBlank(requestBodyParams.get("phone"))) {
	    		 return ResultUtils.rtFailParam(null);
	    	 }
	    	 String phone=String.valueOf(requestBodyParams.get("phone"));
	    	 if(!SignUtils.security(phone, sign)) {
	    		 ResultUtils.rtFailVerification(null);
	    	 }
	    	 //判断手机是否已经存在
	    	 if(loginRegService.isPhoneExist(phone)) {
	    		 return ResultUtils.rtFailParam(null,"手机账号已经存在");
	    	 }
	    	 
	    	 //发送验证码,修改手机
	    	 loginRegService.getVerifyCode(phone,4);
	       return ResultUtils.rtSuccess(null);
	    }
	    /**
	     * 
	        * @Title: modifyPhoneStep2
	        * @Description: TODO(这里用一句话描述这个方法的作用)
	        * @param @param file
	        * @param @param requestBodyParams
	        * @param @return    参数
	        * @return Map<String,Object>    返回类型
	        * @throws
	        * @author LanYeYe
	     */
	    @RequestMapping("modifyPhoneStep2")
	    public Map<String,Object>  modifyPhoneStep2(@RequestParam CommonsMultipartFile file,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
	    	 Long userId=this.getUserId();
	    	 if(userId==null || ValidTools.isBlank(requestBodyParams.get("phone"))) {
	    		 return ResultUtils.rtFailParam(null);
	    	 }
	    	 String phone=String.valueOf(requestBodyParams.get("phone"));
	    	 String verifyCode=String.valueOf(requestBodyParams.get("verifyCode"));
	    	 String status=loginRegService.validVerifyCode(phone,4,verifyCode);
	    	 if(status.equals(LoginRegService.OK)) {
	    		 //更新手机号码
	    		 userInfoService.updatePhone(phone, userId);
	    		 return ResultUtils.rtSuccess(null);
	    	 }else if(status.equals(LoginRegService.VC_ERR)){
	    		 return ResultUtils.rtFailParam(null, "验证码错误");
	    	 }else if(status.equals(LoginRegService.VC_EX)) {
	    		 return ResultUtils.rtFailParam(null, "验证码过期");
	    	 }
	    	 return ResultUtils.rtFail(null);
	    }
	    
	    @RequestMapping("modifyPwdStep1")
	    public Map<String,Object>  modifyPwdStep1(@RequestParam CommonsMultipartFile file,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
	    	 Long userId=this.getUserId();
	    	 if(userId==null  || ValidTools.isBlank(requestBodyParams.get("currentPwd"))) {
	    		 return ResultUtils.rtFailParam(null);
	    	 }
	    	 String currentPwd=String.valueOf(requestBodyParams.get("currentPwd"));
	    	 boolean b=userInfoService.validPwd(currentPwd, userId);
	    	 if(b) {
	    		 ResultUtils.rtSuccess(null);
	    	 }
	    	 return ResultUtils.rtFailParam(null, "密码错误");
	    }
	    @RequestMapping("modifyPwdStep2")
	    public Map<String,Object>  modifyPwdStep2(@RequestParam CommonsMultipartFile file,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
	    	 Long userId=this.getUserId();
	    	 if(userId==null  || ValidTools.isBlank(requestBodyParams.get("orgPwd")) || ValidTools.isBlank(requestBodyParams.get("newPwd"))) {
	    		 return ResultUtils.rtFailParam(null);
	    	 }
	    	 String orgPwd=String.valueOf(requestBodyParams.get("orgPwd"));
	    	 String newPwd=String.valueOf(requestBodyParams.get("newPwd"));
	    	 userInfoService.updatePwd(orgPwd, newPwd, userId);
	    	 return ResultUtils.rtSuccess(null);
	    }
	      
	  public String getImgBasePath() {
		  RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
	   	     HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//	   	     LOG.info(request.getRequestURI());
//	   	     LOG.info(request.getRequestURL().toString());
//	   	     LOG.info(request.getServletPath());  
//	   	     LOG.info(request.getServerName()+"--"+request.getServerPort());
	   	     return "http://"+request.getServerName()+":"+request.getServerPort()+"/userFiles/getImgs?key=";

	  }
}
