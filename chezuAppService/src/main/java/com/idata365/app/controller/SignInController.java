package com.idata365.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.entity.SignatureLogBean;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.service.LoginRegService;
import com.idata365.app.service.SignInService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

@RestController
public class SignInController extends BaseController
{
	@Autowired
	private SignInService signInService;
	
	/**
	 * 最近已经连续签到的日期，格式：yyyyMMdd
	 * @param allRequestParams
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/query")
	public Map<String, Object> query(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) SignatureLogBean reqBean)
	{
		LOG.debug("query param==={}", JSON.toJSONString(reqBean));
		List<String> resultList = this.signInService.query(reqBean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 签到
	 * @param allRequestParams
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/signatureAim")
	public Map<String, Object> signatureAim(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) SignatureLogBean reqBean)
	{
		LOG.debug("query param==={}", JSON.toJSONString(reqBean));
		this.signInService.sign(reqBean);
		return ResultUtils.rtSuccess(null);
	}

}
