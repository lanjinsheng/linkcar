package com.idata365.app.controller.securityV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.DicService;
import com.idata365.app.util.ResultUtils;

@RestController
public class RuleController extends BaseController {
	private static long lastNotifyTime = 0;
	List<Map<String, String>> result = new ArrayList<>();
	protected static final Logger LOG = LoggerFactory.getLogger(RuleController.class);
	@Autowired
	DicService dicService;

	@RequestMapping("/getRules")
	public Map<String, Object> getRules(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long now = System.currentTimeMillis();
		long userId = this.getUserId();
		int ruleType = Integer.valueOf(requestBodyParams.get("ruleType").toString());
		LOG.info("userId==============" + userId);
		if ((now - lastNotifyTime) > (3600 * 1000)) {// 一个小时重新去库获取
			result = dicService.getRulesByType(ruleType);
			lastNotifyTime = now;
		}

		return ResultUtils.rtSuccess(result);
	}

}
