package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.InformService;
import com.idata365.app.util.ResultUtils;

@RestController
public class InformController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(InformController.class);
	@Autowired
	private InformService informService;

	public InformController() {
		System.out.println("InformController");
	}

	/**
	 * 
	 * @Title: queryInformTypeList
	 * @Description: TODO(举报原因列表)
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
	@RequestMapping("/queryInformTypeList")
	public Map<String, Object> queryInformTypeList(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		List<Map<String, String>> list = this.informService.informTypeList();
		rtMap.put("reportList", list);
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 
	 * @Title: submitInform
	 * @Description: TODO(提交举报信息)
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
	@RequestMapping("/submitInform")
	public Map<String, Object> submitInform(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("param==={}", JSON.toJSONString(requestBodyParams));
		Map<String, Object> entity = new HashMap<String, Object>();
		long userId = this.getUserId();
		int informId = Integer.valueOf(requestBodyParams.get("informId").toString()).intValue();
		String informValue = requestBodyParams.get("informValue").toString();
		long defendantId = Long.valueOf(requestBodyParams.get("defendantId").toString()).longValue();
		String remark = requestBodyParams.get("remark").toString();
		entity.put("informId", informId);
		entity.put("informValue", informValue);
		entity.put("plaintiffId", userId);
		entity.put("defendantId", defendantId);
		entity.put("remark", remark);
		this.informService.submitInform(entity);
		return ResultUtils.rtSuccess(null);
	}

}