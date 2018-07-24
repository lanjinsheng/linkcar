package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.LookAdService;
import com.idata365.app.util.ResultUtils;

@RestController
public class LookAdController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(LookAdController.class);
	@Autowired
	public LookAdService lookAdService;

	/**
	 * 
	 * @Title: countOfOddLookAd
	 * @Description: TODO(查询剩余可看广告数量)
	 * @param @return
	 *            参数
	 * @return <Map<String,Object>> 返回类型
	 * @throws @author
	 *             LiXing
	 */

	@RequestMapping("/ad/countOfOddLookAd")
	public Map<String, Object> missionList(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<>();
		long userId = this.getUserId();
		LOG.info("userId=================" + userId);
		int count = this.lookAdService.countOfOddLookAd(userId);
		rtMap.put("oddCount", String.valueOf(count));
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	 * @Title: adCallBack
	 * @Description: TODO(广告回调接口)
	 * @param @return
	 *            参数
	 * @return <Map<String,Object>> 返回类型
	 * @throws @author
	 *             LiXing
	 */

	@RequestMapping("/ad/adCallBack")
	public Map<String, Object> adCallBack(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<>();
		long userId = this.getUserId();
		int adSign = Integer.valueOf(requestBodyParams.get("adSign").toString()).intValue();
		long adPassId = Long.valueOf(requestBodyParams.get("adPassId").toString());
		LOG.info("userId=================" + userId);
		LOG.info("adPassId=================" + adPassId);
		try {
			Map<String, Object> prizeInfo = this.lookAdService.receiveLookAdPower(userId, adSign, adPassId);
			rtMap.put("prizeInfo", prizeInfo);
			return ResultUtils.rtSuccess(rtMap);
		} catch (Exception e) {
			return ResultUtils.rtFail(null, e.getMessage(), "100");
		}
	}
	
}
