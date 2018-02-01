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

import com.idata365.app.service.SystemVisionService;
import com.idata365.app.util.ResultUtils;

/**
 * 版本控制
 * @className:com.idata365.app.controller.SystemVisionController
 * @description:TODO
 * @date:2018年2月1日 上午11:57:34
 * @author:CaiFengYao
 */
@RestController
public class SystemVisionController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(SystemVisionController.class);

	@Autowired
	private SystemVisionService systemVisionService;

	@RequestMapping("/system/verifyVision")
	public Map<String, Object> verifyVision(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, String> requestBodyParams)
	{
		String phoneType = requestBodyParams.get("phoneType");
		String vision = requestBodyParams.get("vision");
		if (phoneType == null || vision == null)
		{
			return ResultUtils.rtFailParam(null, "无效参数");
		}
		int status = systemVisionService.verifyVision(phoneType, vision);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("visionStatus", status);
		return ResultUtils.rtSuccess(map);
	}
}
