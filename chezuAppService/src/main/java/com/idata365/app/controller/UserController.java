package com.idata365.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.idata365.app.config.SystemProperties;
import com.idata365.app.constant.NameConstant;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.serviceV2.ThirdPartyLoginService;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class UserController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private LoginRegService loginRegService;
	@Autowired
	private SystemProperties systemProperties;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ThirdPartyLoginService thirdPartyLoginService;
	@Autowired
	private ChezuAccountService chezuAccountService;

	public UserController() {
		System.out.println("UserController");
	}

	/**
	 * 
	 * @Title: login
	 * @Description: TODO(登录)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/account/login")
	public Map<String, Object> login(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("password")))
			return ResultUtils.rtFailParam(null);
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String password = String.valueOf(requestBodyParams.get("password"));
		String status = LoginRegService.OK;
		// 送审时，去掉验证码。正常场景下，开启验证码（当开关为0时候，关闭验证码。开关为1时，开启验证码）
//		if (SystemConstant.LOGINCODE_SWITCH == 0) {
//			status = LoginRegService.OK;
//		} else {
//			if (ValidTools.isBlank(requestBodyParams.get("verifyCode"))) {
//				return ResultUtils.rtFailParam(null);
//			}
//			String verifyCode = String.valueOf(requestBodyParams.get("verifyCode"));
//			if (verifyCode.equals(systemProperties.getNbcode())) {
//				status = LoginRegService.OK;
//			} else {
//				status = loginRegService.validVerifyCode(phone, 2, verifyCode);
//			}
//		}
		String token = "";
		if (status.equals(LoginRegService.OK)) {// 校验码通过
			UsersAccount account = new UsersAccount();
			status = loginRegService.validAccount(phone, password, account);
			if (status.equals(LoginRegService.OK)) {// 账号通过
				token = UUID.randomUUID().toString().replaceAll("-", "");
				loginRegService.insertToken(account.getId(), token);
				rtMap.put("userId", account.getId());
				rtMap.put("nickName",
						account.getNickName() == null ? PhoneUtils.hidePhone(phone) : account.getNickName());
				rtMap.put("headImg", this.getImgBasePath() + account.getImgUrl());

				Map<String, String> authenticated = chezuAccountService.isAuthenticated(account.getId(),
						SignUtils.encryptHMAC(String.valueOf(account.getId())));
				if ("1".equals(authenticated.get("IdCardIsOK")) && "1".equals(authenticated.get("VehicleTravelIsOK"))) {
					rtMap.put("isAuthenticated", "1");
				} else {
					rtMap.put("isAuthenticated", "0");
				}

			} else {

			}
		} else {

		}
		rtMap.put("status", status);
		rtMap.put("token", token);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	 * @Title: thirdPartyLogin
	 * @Description: TODO(三方登录)
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
	@SuppressWarnings("unused")
	@RequestMapping("/account/thirdPartyLogin")
	public Map<String, Object> thirdPartyLogin(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("loginType"))
				|| ValidTools.isBlank(requestBodyParams.get("openId"))|| ValidTools.isBlank(requestBodyParams.get("remark")))
			return ResultUtils.rtFailParam(null);
		int loginType = Integer.valueOf(String.valueOf(requestBodyParams.get("loginType")));
		String openId = String.valueOf(requestBodyParams.get("openId"));
		LOG.info("openId================================"+openId);
		Map<String, Object> map = thirdPartyLoginService.queryThirdPartyLoginById(openId);
		//封装并插入记录
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("openId", openId);
		entity.put("loginType", loginType);
		entity.put("remark", requestBodyParams.get("remark").toString());
		if(map == null) {
			thirdPartyLoginService.insertLogs(entity);
		}else {
			thirdPartyLoginService.updateLogs(entity);
		}
		
		// 未绑定手机号
		if (map == null||map.get("userId")==null) {
			rtMap.put("status", "PHONE_NO");
			return ResultUtils.rtSuccess(rtMap);// 跳到绑定手机号页面
		}

		// 有绑定手机号
		long userId = Long.valueOf(String.valueOf(map.get("userId")));
		UsersAccount account = userInfoService.getUsersAccount(userId);
		String token = "";
		token = UUID.randomUUID().toString().replaceAll("-", "");
		//重新登录，更新token
		loginRegService.insertToken(account.getId(), token);
		Map<String, Object> bean = new Gson().fromJson(requestBodyParams.get("remark").toString(), new TypeToken<Map<String, Object>>(){}.getType());
		//返回信息
		rtMap.put("userId", account.getId());
		rtMap.put("nickName", bean.get("nickName").toString());
		rtMap.put("headImg", bean.get("headImg").toString());
		rtMap.put("userPhone", account.getPhone());
		
		Map<String, String> authenticated = chezuAccountService.isAuthenticated(account.getId(),
				SignUtils.encryptHMAC(String.valueOf(account.getId())));
		if ("1".equals(authenticated.get("IdCardIsOK")) && "1".equals(authenticated.get("VehicleTravelIsOK"))) {
			rtMap.put("isAuthenticated", "1");
		} else {
			rtMap.put("isAuthenticated", "0");
		}
		rtMap.put("status", "OK");
		rtMap.put("token", token);

		return ResultUtils.rtSuccess(rtMap);

	}

	/**
	 * 
	 * @Title: bindPhone1
	 * @Description: TODO(綁定手机号1)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/account/bindPhone1")
	public Map<String, Object> bindPhone1(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("verifyCode"))|| ValidTools.isBlank(requestBodyParams.get("openId")))
			return ResultUtils.rtFailParam(null);
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String verifyCode = String.valueOf(requestBodyParams.get("verifyCode"));
		String openId = String.valueOf(requestBodyParams.get("openId"));
		LOG.info("openId================================"+openId);
		LOG.info("phone================================"+phone);
		Map<String, Object> map = thirdPartyLoginService.queryThirdPartyLoginById(openId);
		Map<String, Object> bean = new Gson().fromJson(map.get("remark").toString(), new TypeToken<Map<String, Object>>(){}.getType());
		String status = LoginRegService.VC_ERR;
		if (verifyCode.equals(systemProperties.getNbcode())) {
			// 测试使用万能验证码
			status = LoginRegService.OK;
		} else {
			status = loginRegService.validVerifyCode(phone, 1, verifyCode);
		}
		if (status.equals(LoginRegService.OK)) {
			// 校验码通过
			UsersAccount account = loginRegService.getUserByPhone(phone);
			if (account!=null) {
				//号码已经注册，直接登录，刷新token
				String token = "";
				token = UUID.randomUUID().toString().replaceAll("-", "");
				loginRegService.insertToken(account.getId(), token);
				//返回信息
				rtMap.put("userId", account.getId());
				rtMap.put("nickName",bean.get("nickName").toString());
				rtMap.put("headImg",bean.get("headImg").toString());
				rtMap.put("userPhone", account.getPhone());

				Map<String, String> authenticated = chezuAccountService.isAuthenticated(account.getId(),
						SignUtils.encryptHMAC(String.valueOf(account.getId())));
				if ("1".equals(authenticated.get("IdCardIsOK")) && "1".equals(authenticated.get("VehicleTravelIsOK"))) {
					rtMap.put("isAuthenticated", "1");
				} else {
					rtMap.put("isAuthenticated", "0");
				}
				rtMap.put("token", token);
				rtMap.put("status", "OK");
				//绑定三方表
				thirdPartyLoginService.updateByOpenId(account.getId(),openId);
			} else {
				//号码没有注册过，注册，并去设置密码
				rtMap.put("status", "PWD_NO");
				String nickName = bean.get("nickName").toString();
				String headImg = bean.get("headImg").toString();
				UsersAccount accountBean = new UsersAccount();
				accountBean.setImgUrl(headImg);
				//注册
				String token = loginRegService.regUser(phone, "", nickName, rtMap,accountBean);
				//绑定三方表
				thirdPartyLoginService.updateByOpenId(accountBean.getId(),openId);
				if (token == null) {
					return ResultUtils.rtFailRequest(null);
				}
				rtMap.put("token", token);
				rtMap.put("nickName", accountBean.getNickName());
				rtMap.put("headImg", headImg);
				rtMap.put("isAuthenticated", "0");
			}
			return ResultUtils.rtSuccess(rtMap);
		} else {
			if (status.equals(LoginRegService.VC_ERR))
				return ResultUtils.rtFailParam(null, "校验码无效");
			if (status.equals(LoginRegService.VC_EX))
				return ResultUtils.rtFailParam(null, "校验码过期");
		}
		return ResultUtils.rtSuccess(null);
	}
	
	
	/**
	 * 
	 * @Title: bindPhone2
	 * @Description: TODO(绑定电话号码2---填写密码)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             lcc
	 */
	@RequestMapping("/account/bindPhone2")
	public Map<String, Object> bindPhone2(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("password")))
			return ResultUtils.rtFailParam(null);
		String openId = String.valueOf(requestBodyParams.get("openId"));
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String password = String.valueOf(requestBodyParams.get("password"));
		LOG.info("openId================================"+openId);
		LOG.info("phone================================"+phone);
		Map<String, Object> map = thirdPartyLoginService.queryThirdPartyLoginById(openId);
		Map<String, Object> bean = new Gson().fromJson(map.get("remark").toString(), new TypeToken<Map<String, Object>>(){}.getType());
		
		//更新密码
		String nickName = bean.get("nickName").toString()==null?NameConstant.getNickName():bean.get("nickName").toString();
		String headImg = bean.get("headImg").toString()==null?"":bean.get("headImg").toString();
		String token = loginRegService.updateUserPwd(phone, password, rtMap);
		if (token == null) {
			return ResultUtils.rtFailRequest(null);
		}
		//返回信息
		rtMap.put("token", token);
		rtMap.put("nickName", nickName);
		rtMap.put("headImg", headImg);
		rtMap.put("userPhone", phone);
		rtMap.put("isAuthenticated", "0");
		
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	/**
	 * 
	 * @Title: registerStep1
	 * @Description: TODO(注册校验1)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/account/registerStep1")
	public Map<String, Object> registerStep1(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("verifyCode"))|| ValidTools.isBlank(requestBodyParams.get("password")))
			return ResultUtils.rtFailParam(null);
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String verifyCode = String.valueOf(requestBodyParams.get("verifyCode"));
		String password = String.valueOf(requestBodyParams.get("password"));
		String status = LoginRegService.VC_ERR;
		if (verifyCode.equals(systemProperties.getNbcode())) {// 测试使用万能验证码
			status = LoginRegService.OK;
		} else {
			status = loginRegService.validVerifyCode(phone, 1, verifyCode);
		}
		if (status.equals(LoginRegService.OK)) {// 校验码通过
			boolean isAccountExist = loginRegService.isPhoneExist(phone);
			if (isAccountExist) {
				return ResultUtils.rtFailParam(null, "账号已注册");
			} else {
				String nickName = NameConstant.getNickName();
				if(requestBodyParams.get("nickName")!=null){
					nickName=String.valueOf(requestBodyParams.get("nickName"));
				}else{
					
				}
				
				//nick唯一性处理
				
				UsersAccount account = new UsersAccount();
				String token = loginRegService.regUser(phone, password, nickName, rtMap,account);
				if (token == null) {
					return ResultUtils.rtFailRequest(null);
				}
				rtMap.put("token", token);
				rtMap.put("nickName", account.getNickName());
				rtMap.put("headImg", "");
				rtMap.put("isAuthenticated", "0");
				return ResultUtils.rtSuccess(rtMap);
			}
		} else {
			if (status.equals(LoginRegService.VC_ERR))
				return ResultUtils.rtFailParam(null, "校验码无效");
			if (status.equals(LoginRegService.VC_EX))
				return ResultUtils.rtFailParam(null, "校验码过期");
		}
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 
	 * @Title: registerStep2
	 * @Description: TODO(注册提交2---废弃---lcc)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/account/registerStep2")
	public Map<String, Object> registerStep2(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("password")))
			return ResultUtils.rtFailParam(null);
		// 校验是否需要邀请码
//		if (SystemConstant.INVITECODE_SWITCH == 1) {
//			if (ValidTools.isBlank(requestBodyParams.get("inviteCode"))) {
//				return ResultUtils.rtFailParam(null);
//			} else if (!String.valueOf(requestBodyParams.get("inviteCode")).equals(SystemConstant.INVITE_CODE)) {
//				return ResultUtils.rtFailParam(null, "邀请码错误");
//			}
//		}
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String password = String.valueOf(requestBodyParams.get("password"));
		String nickName = NameConstant.getNickName();
		UsersAccount account = new UsersAccount();
		String token = loginRegService.regUser(phone, password, nickName, rtMap,account);
		if (token == null) {
			return ResultUtils.rtFailRequest(null);
		}
		rtMap.put("token", token);
		rtMap.put("nickName", nickName);
		rtMap.put("headImg", "");
		rtMap.put("isAuthenticated", "0");
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: findPasswordStep1
	 * @Description: TODO(找回密码校验1)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/account/findPasswordStep1")
	public Map<String, Object> findPasswordStep1(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("verifyCode"))|| ValidTools.isBlank(requestBodyParams.get("password")))
			return ResultUtils.rtFailParam(null);
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String verifyCode = String.valueOf(requestBodyParams.get("verifyCode"));
		String password = String.valueOf(requestBodyParams.get("password"));
		String status = LoginRegService.VC_ERR;
		if (verifyCode.equals(systemProperties.getNbcode())) {// 测试万能验证码
			status = LoginRegService.OK;
		} else {
			status = loginRegService.validVerifyCode(phone, 3, verifyCode);
		}
		if (status.equals(LoginRegService.OK)) {// 校验码通过
			boolean isAccountExist = loginRegService.isPhoneExist(phone);
			if (isAccountExist) {
				String token = loginRegService.updateUserPwd(phone, password, rtMap);
				if (token == null) {
					return ResultUtils.rtFailRequest(null);
				}
				rtMap.put("token", token);
				return ResultUtils.rtSuccess(rtMap);
			} else {
				return ResultUtils.rtFailParam(null, "账号不存在");
			}
		} else {
			if (status.equals(LoginRegService.VC_ERR))
				return ResultUtils.rtFailParam(null, "校验码无效");
			if (status.equals(LoginRegService.VC_EX))
				return ResultUtils.rtFailParam(null, "校验码过期");
		}
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 
	 * @Title: findPasswordStep2
	 * @Description: TODO(找回密码提交2)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/account/findPasswordStep2")
	public Map<String, Object> findPasswordStep2(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("password")))
			return ResultUtils.rtFailParam(null);
		String phone = String.valueOf(requestBodyParams.get("phone"));
		String password = String.valueOf(requestBodyParams.get("password"));
		String token = loginRegService.updateUserPwd(phone, password, rtMap);
		if (token == null) {
			return ResultUtils.rtFailRequest(null);
		}
		rtMap.put("token", token);
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: sendVerifyCode
	 * @Description: TODO(发送校验码)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	@RequestMapping("/account/sendVerifyCode")
	public Map<String, Object> sendVerifyCode(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = (HttpServletRequest) requestAttributes
				.resolveReference(RequestAttributes.REFERENCE_REQUEST);
		// String sign=request.getHeader("sign");
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("phone"))
				|| ValidTools.isBlank(requestBodyParams.get("codeType")))
			return ResultUtils.rtFailParam(null);
		String phone = String.valueOf(requestBodyParams.get("phone"));
		// boolean signValid=SignUtils.security(phone, sign);
		// if(!signValid) {
		// return ResultUtils.rtFailVerification(null);
		// }
		loginRegService.getVerifyCode(phone, Integer.valueOf(requestBodyParams.get("codeType").toString()));
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 
	 * @Title: addDeviceUserInfo
	 * @Description: TODO(增加设备信息)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/account/addDeviceUserInfo")
	public Map<String, Object> addDeviceUserInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("deviceToken")))
			return ResultUtils.rtFailParam(null);
		String deviceInfo = String.valueOf(requestBodyParams.get("deviceToken"));
		String eventType = String.valueOf(requestBodyParams.get("eventType"));
		long userId = 0;
		if (ValidTools.isNotBlank(requestBodyParams.get("userId"))) {
			userId = Long.valueOf(requestBodyParams.get("userId").toString());
		}
		String alias = loginRegService.addDeviceUserInfo(deviceInfo, userId);
		if (alias == null) {
			return ResultUtils.rtFail(null);
		}
		rtMap.put("alias", alias);
		if (eventType.equals("1")) {// 1为注册事件
			String toUrl = this.getRegSendMsgUrl();
			try {
				toUrl = toUrl + SignUtils.encryptDataAes(String.valueOf(System.currentTimeMillis()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rtMap.put("toUrl", toUrl);
		} else {
			rtMap.put("toUrl", "");
		}
		return ResultUtils.rtSuccess(rtMap);
	}

}