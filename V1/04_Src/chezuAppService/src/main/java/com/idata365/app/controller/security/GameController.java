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
import com.idata365.app.entity.PenalResultBean;
import com.idata365.app.entity.StationResultBean;
import com.idata365.app.entity.TravelHistoryParamBean;
import com.idata365.app.entity.ViolationStatParamBean;
import com.idata365.app.entity.ViolationStatResultAllBean;
import com.idata365.app.entity.bean.UserInfo;
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
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalSpeed")
	public Map<String, Object> penalSpeed(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalSpeed(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalBrake")
	public Map<String, Object> penalBrake(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalBrake(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalTurn")
	public Map<String, Object> penalTurn(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalTurn(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalOverspeed")
	public Map<String, Object> penalOverspeed(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalOverspeed(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalNightDrive")
	public Map<String, Object> penalNightDrive(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalNightDrive(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalTiredDrive")
	public Map<String, Object> penalTiredDrive(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalTiredDrive(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/penalIllegalStop")
	public Map<String, Object> penalIllegalStop(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Long userId = super.getUserId();
		PenalResultBean resultBean = this.gameService.penalIllegalStop(bean, userId);
		List<PenalResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/listStations")
	public Map<String, Object> listStations(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		List<StationResultBean> resultList = this.gameService.listStations(bean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/stopAtStation")
	public Map<String, Object> stopAtStation(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		int result = this.gameService.stopAtStation(bean);
		if (result > 0)
		{
			return ResultUtils.rtSuccess(null);
		}
		else
		{
			return ResultUtils.rtFail(null);
		}
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/holdAtStation")
	public Map<String, Object> holdAtStation(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		int result = this.gameService.holdAtStation(bean);
		if (result > 0)
		{
			return ResultUtils.rtSuccess(null);
		}
		else
		{
			return ResultUtils.rtFail(null);
		}
	}
	
	/**
	 * 通知家族内其他人去贴条
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/informOtherToPenalty")
	public Map<String, Object> informOtherToPenalty(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		UserInfo userInfo = super.getUserInfo();
		this.gameService.informOtherToPenalty(bean, userInfo);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 隐藏该段行程
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/hiddenTravel")
	public Map<String, Object> hiddenTravel(@RequestBody TravelHistoryParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		int result = this.gameService.hiddenTravel(bean);
		if (result > 0)
		{
			return ResultUtils.rtSuccess(null);
		}
		else
		{
			return ResultUtils.rtFail(null);
		}
	}
}
