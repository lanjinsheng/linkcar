package com.idata365.app.controller.security;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.idata365.app.controller.BaseController;
import com.idata365.app.entity.SigAwardStatBean;
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
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/query")
	public Map<String, Object> query(@RequestBody SignatureDayLogBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		List<String> resultList = this.signInService.query(reqBean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 查询签到状态（抽奖状态）
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/querySigAwardStat")
	public Map<String, Object> querySigAwardStat(@RequestBody SignatureDayLogBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		SigAwardStatBean resultBean = this.signInService.querySigAwardStat(reqBean);
		return ResultUtils.rtSuccess(resultBean);
	}
	
	/**
	 * 查询连续签到天数
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/queryContinueSigDay")
	public Map<String, Object> queryContinueSigDay(@RequestBody SignatureDayLogBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		int num = this.signInService.queryContinueSigDay(reqBean);
		JSONObject resultObj = new JSONObject();
		resultObj.put("num", String.valueOf(num));
		return ResultUtils.rtSuccess(resultObj);
	}
	
	/**
	 * 签到
	 * @param allRequestParams
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/om/signatureAim")
	public Map<String, Object> signatureAim(@RequestBody SignatureDayLogBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		this.signInService.sign(reqBean);
		return ResultUtils.rtSuccess(null);
	}

}
