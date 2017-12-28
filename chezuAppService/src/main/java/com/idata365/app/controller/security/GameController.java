package com.idata365.app.controller.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.ViolationStatParamBean;
import com.idata365.app.entity.ViolationStatResultAllBean;
import com.idata365.app.service.GameService;
import com.idata365.app.util.ResultUtils;

@RestController
public class GameController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	
	/**
	 * 违规情况
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/violationList")
	public Map<String, Object> violationList(@RequestBody ViolationStatParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		ViolationStatResultAllBean resultBean = this.gameService.violationList(bean);
		List<ViolationStatResultAllBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 挑战家族
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/challengeFamily")
	public Map<String, Object> challengeFamily(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		this.gameService.challengeFamily(bean);
		return ResultUtils.rtSuccess(null);
	}
}
