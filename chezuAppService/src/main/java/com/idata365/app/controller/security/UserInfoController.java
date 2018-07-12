package com.idata365.app.controller.security;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.UserConfig;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.QQSSOTools;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.serviceV2.ThirdPartyLoginService;
import com.idata365.app.util.ImageUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;
@RestController
public class UserInfoController extends BaseController{
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LoginRegService loginRegService;
	@Autowired
	private ThirdPartyLoginService thirdPartyLoginService;
	
	
    @Autowired
	SystemProperties systemProperties;
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
		  Long userId=this.getUserId();
		  String nickName=String.valueOf(requestBodyParams.get("nickName"));
		  if(userInfoService.hadAccountByNick(nickName)){
			  return ResultUtils.rtFailParam(null, "昵称已被占用"); 
			}
		  userInfoService.updateNickName(userId, nickName);
		  return ResultUtils.rtSuccess(null);
	  }
	  /**
	   * 
	      * @Title: enableStranger
	      * @Description: TODO(这里用一句话描述这个方法的作用)
	      * @param @param allRequestParams
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  
	  @RequestMapping("/user/enableStranger")
	  public Map<String,Object> enableStranger(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		  Long userId=this.getUserId();
		  String enableStranger=String.valueOf(requestBodyParams.get("enableStranger"));
		  userInfoService.updateEnableStranger(userId, Integer.valueOf(enableStranger));
		  return ResultUtils.rtSuccess(null);
	  }  
	  
	    @RequestMapping(value = "/user/uploadHeadImg",method = RequestMethod.POST)
	    public Map<String,Object>  uploadHeadImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) throws IOException {
	    	Long userId=this.getUserId();
	    	Map<String,String> rtMap=new HashMap<String,String>();
	    	try {
	    		 String key="";
	    	      if(systemProperties.getSsoQQ().equals("1")) {//走qq
	    	    	  key=QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.HEADER);
	    	    	  File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
	        		  File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
	                  file.transferTo(dealFile);
	                  QQSSOTools.saveOSS(dealFile,key);
	    	      }else {//走阿里
	               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
	    		     key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.HEADER);
	               InputStream is=file.getInputStream();
	               SSOTools.saveOSS(is,key);
	               is.close();
	    	      }
	               rtMap.put("imgUrl", getImgBasePath()+key);
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
	    @RequestMapping("/user/modifyPhoneStep1")
	    public Map<String,Object>  modifyPhoneStep1(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
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
	    @RequestMapping("/user/modifyPhoneStep2")
	    public Map<String,Object>  modifyPhoneStep2(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
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
	    
	    @RequestMapping("/user/modifyPwdStep1")
	    public Map<String,Object>  modifyPwdStep1(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
	    	 Long userId=this.getUserId();
	    	 if(userId==null  || ValidTools.isBlank(requestBodyParams.get("currentPwd"))) {
	    		 return ResultUtils.rtFailParam(null);
	    	 }
	    	 String currentPwd=String.valueOf(requestBodyParams.get("currentPwd"));
	    	 boolean b=userInfoService.validPwd(currentPwd, userId);
	    	 if(b) {
	    	    return	 ResultUtils.rtSuccess(null);
	    	 }
	    	 return ResultUtils.rtFailParam(null, "密码错误");
	    }
	    @RequestMapping("/user/modifyPwdStep2")
	    public Map<String,Object>  modifyPwdStep2(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
	    	 Long userId=this.getUserId();
	    	 if(userId==null  || ValidTools.isBlank(requestBodyParams.get("orgPwd")) || ValidTools.isBlank(requestBodyParams.get("newPwd"))) {
	    		 return ResultUtils.rtFailParam(null);
	    	 }
	    	 String orgPwd=String.valueOf(requestBodyParams.get("orgPwd"));
	    	 String newPwd=String.valueOf(requestBodyParams.get("newPwd"));
	    	 userInfoService.updatePwd(orgPwd, newPwd, userId);
	    	 return ResultUtils.rtSuccess(null);
	    }
	      
	  /**
	   * 
	      * @Title: uploadFrontDrivingLicenseImg
	      * @Description: TODO(驾驶证正面)
	      * @param @param file
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  @RequestMapping("/user/uploadFrontDrivingLicenseImg")
	  public  Map<String,Object>  uploadFrontDrivingLicenseImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) {
	    	 Long userId=this.getUserId();
	    	 if(file==null) {
	    		 return ResultUtils.rtFailParam(null,"附件为空");
	    	 }
	    	Map<String,Object> rtMap=new HashMap<String,Object>();
	    			rtMap.put("userId", userId);
	            	rtMap.put("birthday", "");
	    			rtMap.put("validYears", "");
	    			rtMap.put("validDay", "");
	    			rtMap.put("virginDay", "");
	    			rtMap.put("name", "");
	    			rtMap.put("driveCardType", "");
	    			rtMap.put("gender", "");
	    			rtMap.put("nation", "C");
		    try {
		    	 String key="";
	    	      if(systemProperties.getSsoQQ().equals("1")) {//走qq
	    	    	  key=QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE1);
	    	    	  File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
	        		  File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
	                  file.transferTo(dealFile);
	                  QQSSOTools.saveOSS(dealFile,key);
	                  ImageUtils.dealImgJSZ(dealFile,rtMap);
	    	      }else {//走阿里
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE1);
		    		      //处理图片
		               File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
		               File fileParent = dealFile.getParentFile();
		               if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               is.close();
		               file.transferTo(dealFile);
		               ImageUtils.dealImgJSZ(dealFile,rtMap);
	    	      }
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		              //插入更新驾驶证
		               userInfoService.insertImgDriver1(rtMap);
		               
		        }catch (Exception e) {
		               // TODO Auto-generated catch block
		            e.printStackTrace();
		            return ResultUtils.rtFail(null); 
		        }
		    	rtMap.remove("key");
		    	rtMap.remove("userId");
		       return ResultUtils.rtSuccess(rtMap);
	    }
	  
	  /**
	   * 
	      * @Title: uploadBackDrivingLicenseImg
	      * @Description: TODO(驾驶证背面)
	      * @param @param file
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  @RequestMapping("/user/uploadBackDrivingLicenseImg")
	  public  Map<String,Object>  uploadBackDrivingLicenseImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) {
	    	 Long userId=this.getUserId();
	    	 if(file==null) {
	    		 return ResultUtils.rtFailParam(null,"附件为空");
	    	 }
	    	Map<String,Object> rtMap=new HashMap<String,Object>();
	    			rtMap.put("userId", userId);
	            	rtMap.put("imgUrl", "");
	    		 
		    try {
		    	 String key="";
	    	      if(systemProperties.getSsoQQ().equals("1")) {//走qq
	    	    	  key=QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE2);
	    	    	  File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
	        		  File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
	                  file.transferTo(dealFile);
	                  QQSSOTools.saveOSS(dealFile,key);
	    	      }else {//走阿里
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE2);
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               is.close();
	    	      }
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		               
		               //处理图片
//		               File   dealFile = new File(systemProperties.getFileTmpDir()+key);
//		               file.transferTo(dealFile);
//		               ImageUtils.dealImgJSZ(dealFile,rtMap);
		              //插入更新驾驶证
		               userInfoService.insertImgDriver2(rtMap);
		               
		        }catch (Exception e) {
		               // TODO Auto-generated catch block
		            e.printStackTrace();
		            return ResultUtils.rtFail(null); 
		        }
		        rtMap.remove("key");
		    	rtMap.remove("userId");
		       return ResultUtils.rtSuccess(rtMap);
	    }
	  
	  @RequestMapping("/user/modifydrivingLicense")
	  public  Map<String,Object>  modifydrivingLicense(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		  Map<String,Object> rtMap=new HashMap<String,Object>();
		  requestBodyParams.put("userId", this.getUserId());
		  requestBodyParams.put("isDrivingEdit", 0);
		  userInfoService.modifydrivingLicense(requestBodyParams);
		  return ResultUtils.rtSuccess(rtMap);
	  }
	  
	  
	  /**
	   * 
	      * @Title: uploadFrontTravelLicenseImg
	      * @Description: TODO(行驶证正面)
	      * @param @param file
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  
	  @RequestMapping("/user/uploadFrontTravelLicenseImg")
	  public  Map<String,Object>  uploadFrontTravelLicenseImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) {
	    	 Long userId=this.getUserId();
	    	 if(file==null) {
	    		 return ResultUtils.rtFailParam(null,"附件为空");
	    	 }
	    	Map<String,Object> rtMap=new HashMap<String,Object>();
	    	
	    			rtMap.put("userId", userId);
	            	rtMap.put("engineNo", "");
	    			rtMap.put("carType", "1");
	    			rtMap.put("useType", "FYY");
	    			rtMap.put("modelType", "");
	    			rtMap.put("vin", "");
	    			rtMap.put("plateNo", "");
		    try {
		    	 String key="";
	    	      if(systemProperties.getSsoQQ().equals("1")) {//走qq
	    	    	  key=QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE1);
	    	    	  File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
	        		  File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
	                  file.transferTo(dealFile);
	                  QQSSOTools.saveOSS(dealFile,key);
	                  ImageUtils.dealImgXSZ(dealFile,rtMap);
	    	      }else {//走阿里
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		     key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE1);
		    		    
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
		               File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
		               file.transferTo(dealFile);
		               ImageUtils.dealImgXSZ(dealFile,rtMap);
		               is.close();
	    	      }
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		              //插入更新驾驶证
		               userInfoService.insertImgVehicle1(rtMap);
		               
		        }catch (Exception e) {
		               // TODO Auto-generated catch block
		            e.printStackTrace();
		            return ResultUtils.rtFail(null); 
		        }
		    	rtMap.remove("key");
		    	rtMap.remove("userId");
		       return ResultUtils.rtSuccess(rtMap);
	    }
	  @RequestMapping("/user/uploadBackTravelLicenseImg")
	  public  Map<String,Object>  uploadBackTravelLicenseImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) {
	    	 Long userId=this.getUserId();
	    	 if(file==null) {
	    		 return ResultUtils.rtFailParam(null,"附件为空");
	    	 }
	    	Map<String,Object> rtMap=new HashMap<String,Object>();
	    			rtMap.put("userId", userId);
	            	rtMap.put("imgUrl", "");
	    		 
		    try {
		    	 String key="";
	    	      if(systemProperties.getSsoQQ().equals("1")) {//走qq
	    	    	  key=QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE2);
	    	    	  File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
	        		  File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
	                  file.transferTo(dealFile);
	                  QQSSOTools.saveOSS(dealFile,key);
	    	      }else {//走阿里
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE2);
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               is.close();
	    	      }
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		               
		               //处理图片
//		               File   dealFile = new File(systemProperties.getFileTmpDir()+key);
//		               file.transferTo(dealFile);
//		               ImageUtils.dealImgJSZ(dealFile,rtMap);
		              //插入更新驾驶证
		               userInfoService.insertImgVehicle2(rtMap);
		               
		        }catch (Exception e) {
		               // TODO Auto-generated catch block
		            e.printStackTrace();
		            return ResultUtils.rtFail(null); 
		        }
		       rtMap.remove("key");
		    	rtMap.remove("userId");
		       return ResultUtils.rtSuccess(rtMap);
	    }
	  @RequestMapping("/user/modifyVehicleTravelLicense")
	  public  Map<String,Object>  modifyVehicleTravelLicense(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		  Map<String,Object> rtMap=new HashMap<String,Object>();
		  requestBodyParams.put("userId", this.getUserId());
		  requestBodyParams.put("isTravelEdit", 0);
		  userInfoService.modifyVehicleLicense(requestBodyParams);
		  return ResultUtils.rtSuccess(rtMap);
	  }
	  public static void main(String []args) {
			Map<String,Object> rtMap=new HashMap<String,Object>();
			rtMap.put("userId", 999999999);
        	rtMap.put("birthday", "");
			rtMap.put("validYears", "");
			rtMap.put("validDay", "");
			rtMap.put("virginDay", "");
			rtMap.put("name", "");
			rtMap.put("driveCardType", "");
			rtMap.put("gender", "");
			rtMap.put("nation", "C");
    	 String key="";
	    
	    	  key=QQSSOTools.createSSOUsersImgInfoKey(999999999, UserImgsEnum.DRIVER_LICENSE1);
	    	  File   dealFile = new File("D:/dev/1优数/红点抽奖/xszdemo.jpg");
    		  File fileParent = dealFile.getParentFile();  
    			if(!fileParent.exists()){  
    			    fileParent.mkdirs();  
    			} 
              try {
				QQSSOTools.saveOSS(dealFile,key);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//              ImageUtils.dealImgJSZ(dealFile,rtMap);
              ImageUtils.dealImgJSZ(dealFile,rtMap);
              System.out.println(rtMap.size());
	  }
	  /**
	   * 
	      * @Title: gpsHidden
	      * @Description: TODO(Gps设置隐藏)
	      * @param @param allRequestParams
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  @RequestMapping("/user/gpsHidden")
	  public  Map<String,Object>  gpsHidden(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		  String gpsHidden=String.valueOf(requestBodyParams.get("gpsHidden"));
		  userInfoService.updateUserConfig(gpsHidden, this.getUserId(),1);
		  return ResultUtils.rtSuccess(null);
	  }
	  /**
	   * 
	      * @Title: joinMyClub
	      * @Description: TODO(加入我俱乐部设置)
	      * @param @param allRequestParams
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author lcc
	   */
	  @RequestMapping("/user/joinMyClub")
	  public  Map<String,Object>  joinMyClub(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		  String joinMyClub=String.valueOf(requestBodyParams.get("joinMyClub"));
		  userInfoService.updateUserConfig(joinMyClub, this.getUserId(),3);
		  return ResultUtils.rtSuccess(null);
	  }
	  /**
	   * 
	      * @Title: inviteMe
	      * @Description: TODO(玩家招募我设置)
	      * @param @param allRequestParams
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author lcc
	   */
	  @RequestMapping("/user/inviteMe")
	  public  Map<String,Object>  inviteMe(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		  String inviteMe=String.valueOf(requestBodyParams.get("inviteMe"));
		  userInfoService.updateUserConfig(inviteMe, this.getUserId(),2);
		  return ResultUtils.rtSuccess(null);
	  }
	  /**
	   * 
	      * @Title: getUserConfig
	      * @Description: TODO(用户配置获取)
	      * @param @param allRequestParams
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  @RequestMapping("/user/getUserConfig")
	  public  Map<String,Object>  getUserConfig(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		  UserConfig userConfig=userInfoService.getUserConfig(this.getUserId());
		  String gpsHidden="0";
		  if(userConfig==null) {
			  gpsHidden="1";
		  }else {
			  gpsHidden=String.valueOf(userConfig.getUserConfigValue());
		  }
		  Map<String,Object> rtMap=new HashMap<String,Object>();
		  rtMap.put("gpsHidden", gpsHidden);
		  return ResultUtils.rtSuccess(rtMap);
	  }
	  
	/**
	 * 
	 * @Title: queryThirdPartyLoginStatus
	 * @Description: TODO(三方登录状态展示)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/user/queryThirdPartyLoginStatus")
	public Map<String, Object> queryThirdPartyLoginStatus(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		long userId = this.getUserId();
		int queryWXIsBind = thirdPartyLoginService.queryWXIsBind(userId);
		int queryQQIsBind = thirdPartyLoginService.queryQQIsBind(userId);
		rtMap.put("queryWXIsBind", String.valueOf(queryWXIsBind));
		rtMap.put("queryQQIsBind", String.valueOf(queryQQIsBind));
		return ResultUtils.rtSuccess(rtMap);

	}

	/**
	 * 
	 * @Title: unBindTirdPartyLogin
	 * @Description: TODO(三方登录解绑)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/user/unBindTirdPartyLogin")
	public Map<String, Object> unBindTirdPartyLogin(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("partyType")))
			return ResultUtils.rtFailParam(null);
		int partyType = Integer.valueOf(String.valueOf(requestBodyParams.get("partyType")));
		thirdPartyLoginService.unBind(userId,partyType);
		return ResultUtils.rtSuccess(null);

	}
	
	/**
	 * 
	 * @Title: bindTirdPartyLogin
	 * @Description: TODO(三方登录绑定)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             Lcc
	 */
	@RequestMapping("/user/bindTirdPartyLogin")
	public Map<String, Object> bindTirdPartyLogin(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("loginType"))
				|| ValidTools.isBlank(requestBodyParams.get("openId"))|| ValidTools.isBlank(requestBodyParams.get("remark")))
			return ResultUtils.rtFailParam(null);
		long userId = this.getUserId();
		String openId = String.valueOf(requestBodyParams.get("openId"));
		int loginType = Integer.valueOf(String.valueOf(requestBodyParams.get("loginType")));
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("userId", userId);
		entity.put("openId", openId);
		entity.put("loginType", loginType);
		entity.put("remark", requestBodyParams.get("remark").toString());
		thirdPartyLoginService.insertLogs(entity);
		return ResultUtils.rtSuccess(null);
	}
}
