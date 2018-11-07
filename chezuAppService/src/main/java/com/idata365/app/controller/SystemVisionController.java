package com.idata365.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.constant.SystemConstant;
import com.idata365.app.service.SystemVisionService;
import com.idata365.app.util.ResultUtils;

@RestController
public class SystemVisionController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(SystemVisionController.class);

	@Autowired
	private SystemVisionService systemVisionService;
	@Autowired
	private SystemProperties systemProperties;
	
	

	/**
	 * 校验系统版本号
	 * @Description:
	 * @author:CaiFengYao
	 * @date:2018年3月1日 上午10:13:51
	 */
	@RequestMapping("/system/verifyVision")
	public Map<String, Object> verifyVision(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, String> requestBodyParams)
	{
		if (requestBodyParams == null)
		{
			return ResultUtils.rtFailParam(null, "无效版本参数");
		}
		String phoneType = requestBodyParams.get("phoneType");
		String vision = requestBodyParams.get("vision");
		LOG.info("phoneType="+phoneType+"===vision="+vision);
		if (phoneType == null || vision == null)
		{
			return ResultUtils.rtFailParam(null, "无效参数");
		}
		// 业务处理
		Map<String, Object> map = systemVisionService.verifyVision(phoneType, vision);
		return ResultUtils.rtSuccess(map);
	}
	@RequestMapping("/system/initConfig")
	public Map<String, Object> initConfig(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, String> requestBodyParams)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		if (requestBodyParams == null)
		{
			return ResultUtils.rtFailParam(null, "无效版本参数");
		}
		String phoneType = requestBodyParams.get("phoneType");
		String userId = requestBodyParams.get("userId");
		String version = requestBodyParams.get("version");
		LOG.info("phoneType="+phoneType+"===version="+version+"==userId="+userId);
		if (version == null)
		{
			return ResultUtils.rtFailParam(null, "无效参数");
		}
		Integer versionInt=Integer.valueOf(version.replace(".", ""));
		Integer sysVersionInt=Integer.valueOf(systemProperties.getSysVersion().replace(".", ""));
		// 业务处理
		if(versionInt<=sysVersionInt){
			//正式环境1
			map.put("appBaseUrl", "https://product-app.idata365.com");
			map.put("colBaseUrl", "https://product-col.idata365.com/v1");
			map.put("imgBaseUrl", "http://product-app.idata365.com/zuul");
			map.put("imBaseUrl", "ws://47.100.208.65:7397/websocket");
		}else{
			//测试环境1
			map.put("appBaseUrl", "http://120.253.162.2:8769");
			map.put("colBaseUrl", "http://120.253.162.2:9081/v1");
			map.put("imgBaseUrl", "http://120.253.162.2:8769/zuul");
			map.put("imBaseUrl", "ws://120.253.162.2:7397/websocket");
		}
		return ResultUtils.rtSuccess(map);
	}
	/**
	 * 查询邀请码开关
	 * @Description:
	 * @author:CaiFengYao
	 * @date:2018年3月1日 上午10:13:51
	 */
	@RequestMapping("/system/queryInviteSwitch")
	public Map<String, Object> queryInviteSwitch() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inviteSwitchStatus", SystemConstant.INVITECODE_SWITCH);
		map.put("loginValidStatus", SystemConstant.LOGINCODE_SWITCH);
		return ResultUtils.rtSuccess(map);
	}
}
