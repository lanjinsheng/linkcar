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
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.GsonUtils;
import com.idata365.app.util.ImageUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.StaticDatas;
import com.idata365.app.util.ValidTools;
@RestController
public class UserInfoController extends BaseController{
	private final static Logger LOG = LoggerFactory.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private LoginRegService loginRegService;
	
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
	  
	  
	  /**
	   * 
	      * @Title: getUserInfo
	      * @Description: TODO(获取证件信息)
	      * @param @param allRequestParams
	      * @param @param requestBodyParams
	      * @param @return    参数
	      * @return Map<String,Object>    返回类型
	      * @throws
	      * @author LanYeYe
	   */
	  @RequestMapping("/user/getUserInfo")	  
	  public Map<String,Object> getUserInfo(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		  String imgBase=getImgBasePath();
		  Map<String,String> rtMap=new HashMap<String,String>();
		  Long userId=Long.valueOf(requestBodyParams.get("userId").toString());
		  LicenseDriver licenseDrive=userInfoService.getLicenseDriver(userId);
		  LicenseVehicleTravel licenseVehicleTravel=userInfoService.getLicenseVehicleTravel(userId);
		  rtMap.put("enableStranger",String.valueOf(userInfoService.getEnableStranger(userId)));
		  if(licenseDrive!=null) {
			  rtMap.put("userName", licenseDrive.getUserName());
			  rtMap.put("gender",licenseDrive.getGender());
			  rtMap.put("nation",licenseDrive.getNation());
			  rtMap.put("driveCardType",licenseDrive.getDriveCardType());
			  rtMap.put("birthday",licenseDrive.getBirthday());
			  rtMap.put("virginDay",licenseDrive.getVirginDay());
			  rtMap.put("validYears",String.valueOf(licenseDrive.getValidYears()));
			  rtMap.put("frontDrivingImg",imgBase+licenseDrive.getFrontImgUrl());
			  rtMap.put("backDrivingImg",imgBase+licenseDrive.getBackImgUrl());
		  }else {
			  rtMap.put("userName", "");
			  rtMap.put("gender","");
			  rtMap.put("nation","");
			  rtMap.put("driveCardType","");
			  rtMap.put("birthday","");
			  rtMap.put("virginDay","");
			  rtMap.put("validYears","");
			  rtMap.put("frontDrivingImg","");
			  rtMap.put("backDrivingImg","");
		  }
		  if(licenseVehicleTravel!=null) {
			  rtMap.put("plateNo",licenseVehicleTravel.getPlateNo());
			  rtMap.put("cardTypeDesc",StaticDatas.VEHILCE.get(String.valueOf(licenseVehicleTravel.getCarType())));
			  rtMap.put("userTypeDesc",StaticDatas.VEHILCE_USETYPE.get(licenseVehicleTravel.getUseType()));
			  rtMap.put("modelTypeDesc",licenseVehicleTravel.getModelType());
			  rtMap.put("vin",licenseVehicleTravel.getVin());
			  rtMap.put("engineNo",licenseVehicleTravel.getEngineNo());
			  rtMap.put("frontTravelImg",imgBase+licenseVehicleTravel.getFrontImgUrl());
			  rtMap.put("backTravelImg",imgBase+licenseVehicleTravel.getBackImgUrl());
			  rtMap.put("issueDate",licenseVehicleTravel.getIssueDate());
			  rtMap.put("regDate",licenseVehicleTravel.getRegDate());
		  }else {
			  rtMap.put("plateNo","");
			  rtMap.put("cardTypeDesc","");
			  rtMap.put("userTypeDesc","");
			  rtMap.put("modelTypeDesc","");
			  rtMap.put("vin","");
			  rtMap.put("engineNo","");
			  rtMap.put("frontTravelImg","");
			  rtMap.put("backTravelImg","");
			  rtMap.put("issueDate","");
			  rtMap.put("regDate",""); 
		  }
		  return ResultUtils.rtSuccess(rtMap);
	  }
	  @RequestMapping("/user/getUserBaseInfo")	  
	  public Map<String,Object> getUserBaseInfo(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
		  String imgBase=getImgBasePath();
		  Map<String,String> rtMap=new HashMap<String,String>();
		  UserInfo userInfo=this.getUserInfo();
		  rtMap.put("phone", userInfo.getPhone());
		  rtMap.put("nickName", userInfo.getNickName());
		  rtMap.put("headImg", imgBase+userInfo.getImgUrl());
		  return ResultUtils.rtSuccess(rtMap);
	  }
	    @RequestMapping(value = "/user/uploadHeadImg",method = RequestMethod.POST)
	    public Map<String,Object>  uploadHeadImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) throws IOException {
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
	    		 ResultUtils.rtSuccess(null);
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
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   String key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE1);
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		               is.close();
		               //处理图片
		               File   dealFile = new File(systemProperties.getFileTmpDir()+key);
		               file.transferTo(dealFile);
		               ImageUtils.dealImgJSZ(dealFile,rtMap);
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
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   String key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.DRIVER_LICENSE2);
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		               is.close();
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
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   String key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE1);
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		               is.close();
		               //处理图片
		               File   dealFile = new File(systemProperties.getFileTmpDir()+key);
		               file.transferTo(dealFile);
		               ImageUtils.dealImgXSZ(dealFile,rtMap);
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
		               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
		    		   String key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.VEHICLE_LICENSE2);
		               InputStream is=file.getInputStream();
		               SSOTools.saveOSS(is,key);
		               rtMap.put("imgUrl", getImgBasePath()+key);
		               rtMap.put("key", key);
		               is.close();
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
		  userInfoService.modifyVehicleLicense(requestBodyParams);
		  return ResultUtils.rtSuccess(rtMap);
	  }
}
