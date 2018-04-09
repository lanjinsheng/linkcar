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

import com.idata365.app.constant.SystemConstant;
import com.idata365.app.service.SystemVisionService;
import com.idata365.app.util.ResultUtils;

@RestController
public class SystemVisionController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(SystemVisionController.class);

	@Autowired
	private SystemVisionService systemVisionService;

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

	/**
	 * 查询邀请码开关
	 * @Description:
	 * @author:CaiFengYao
	 * @date:2018年3月1日 上午10:13:51
	 */
	@RequestMapping("/system/queryInviteSwitch")
	public Map<String, Object> queryInviteSwitch()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inviteSwitchStatus", SystemConstant.INVITECODE_SWITCH);
		map.put("loginValidStatus", SystemConstant.LOGINCODE_SWITCH);
		return ResultUtils.rtSuccess(map);
	}
}
