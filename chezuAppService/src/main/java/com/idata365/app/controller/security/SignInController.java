package com.idata365.app.controller.security;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.controller.BaseController;
import com.idata365.app.entity.SignInResultBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.service.SignInService;
import com.idata365.app.util.ResultUtils;

@RestController
public class SignInController extends BaseController
{
	@Autowired
	private SignInService signInService;
	
	/**
	 * 查询签到记录，格式：yyyyMMdd
	 * @param allRequestParams
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/query")
	public Map<String, Object> query(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) SignatureDayLogBean reqBean)
	{
		LOG.debug("param==={}", JSON.toJSONString(reqBean));
		SignInResultBean resultBean = this.signInService.query(reqBean);
		return ResultUtils.rtSuccess(resultBean);
	}
	
	/**
	 * 签到
	 * @param allRequestParams
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/signatureAim")
	public Map<String, Object> signatureAim(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) SignatureDayLogBean reqBean)
	{
		LOG.debug("param==={}", JSON.toJSONString(reqBean));
		this.signInService.sign(reqBean);
		return ResultUtils.rtSuccess(null);
	}

}
