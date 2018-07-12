package com.idata365.app.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.LotteryNotEnoughException;
import com.idata365.app.constant.RoleConstant;
import com.idata365.app.entity.CompetitorFamilyInfoResultBean;
import com.idata365.app.entity.FamilyInfoResultBean;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.JudgeChallengeResultBean;
import com.idata365.app.entity.PenalResultBean;
import com.idata365.app.entity.ReadyLotteryBean;
import com.idata365.app.entity.ReadyLotteryResultBean;
import com.idata365.app.entity.ReviewParamBean;
import com.idata365.app.entity.ReviewResultBean;
import com.idata365.app.entity.RoleCountResultBean;
import com.idata365.app.entity.StationResultBean;
import com.idata365.app.entity.SwitchLotteryParamBean;
import com.idata365.app.entity.SwitchRoleResultBean;
import com.idata365.app.entity.TravelHistoryParamBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserRoleLog;
import com.idata365.app.entity.ViolationStatParamBean;
import com.idata365.app.entity.ViolationStatResultAllBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.service.GameService;
import com.idata365.app.service.UserRoleLogService;
import com.idata365.app.util.ResultUtils;

@RestController
public class GameController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(GameController.class);
	
	@Autowired
	private GameService gameService;
	@Autowired
	UserRoleLogService userRoleLogService;
	
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
		int resultFlag = this.gameService.challengeFamily(bean);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("challengeFlag", String.valueOf(resultFlag));
		return ResultUtils.rtSuccess(resultMap);
	}
	
	/**
	 * 挑战家族
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/challengeFamilyV1_1")
	public Map<String, Object> challengeFamilyV1_1(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		Map<String, Object> resultMap = this.gameService.challengeFamilyV1_1(bean,this.getImgBasePath(),this.getUserInfo());
		if(resultMap.get("error")==null) {
			return ResultUtils.rtSuccess(resultMap);
		}else {
			return ResultUtils.rtFailParam(null,resultMap.get("error").toString());
		}
	
	}
	
	
	/**
	 * 查询正在对战的家族系信息---由Game ControllerV2中同名方法覆盖
	 * @param bean
	 * @return
	 */
	@RequestMapping("/gamexxxxx")
	public Map<String, Object> queryCompetitorFamilyInfo(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		CompetitorFamilyInfoResultBean resultBean = this.gameService.queryCompetitorFamilyInfo(bean);
		
		if (null == resultBean)
		{
			return ResultUtils.rtSuccess(null);
		}
		
		String imgBasePath = super.getImgBasePath();
		String imgUrl = resultBean.getImgUrl();
		if (StringUtils.isNotBlank(imgUrl))
		{
			resultBean.setImgUrl(imgBasePath + imgUrl);
		}
		
		return ResultUtils.rtSuccess(resultBean);
	}
	
	/**
	 * 查询家族当天是否发起了第二天的挑战
	 * @param bean
	 * @return
	 */
//	@RequestMapping("/game/judgeChallengeFlag")
//	public Map<String, Object> judgeChallengeFlag(@RequestBody GameFamilyParamBean bean)
//	{
//		LOG.info("param==={}", JSON.toJSONString(bean));
//		
//		JudgeChallengeResultBean resultBean = this.gameService.judgeChallengeFlag(bean);
//		FamilyInfoResultBean familyObj = resultBean.getFamilyObj();
//		if (null != familyObj)
//		{
//			String imgUrl = familyObj.getImgUrl();
//			if (StringUtils.isNoneBlank(imgUrl))
//			{
//				String imgBasePath = super.getImgBasePath();
//				familyObj.setImgUrl(imgBasePath + imgUrl);
//			}
//		}
//		
//		return ResultUtils.rtSuccess(resultBean);
//	}
	@RequestMapping("/game/judgeChallengeFlag")
	public Map<String, Object> judgeChallengeFlag(@RequestBody GameFamilyParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		Map<String,Object> resultBean = this.gameService.judgeChallengeFlag(bean,this.getImgBasePath());
		
		return ResultUtils.rtSuccess(resultBean);
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
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("status", String.valueOf(result));
		
		return ResultUtils.rtSuccess(resultMap);
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
	
	/**
	 * 切换明日角色
	 * @param bean
	 * @return
	 */
//	@RequestMapping("/game/switchRole")
//	public Map<String, Object> switchRole(@RequestBody UserFamilyRoleLogParamBean bean)
//	{
//		LOG.info("param==={}", JSON.toJSONString(bean));
//		bean.setUserId(this.getUserId());
//		SwitchRoleResultBean resultBean = this.gameService.switchRole(bean);
//		return ResultUtils.rtSuccess(resultBean);
//	}
	@RequestMapping("/game/switchRole")
	public Map<String, Object> switchRole(@RequestBody Map<String,Object> map)
	{
		
		long userId=this.getUserId();
		Integer role=Integer.valueOf(map.get("role").toString());
		userRoleLogService.insertUserRoleLog(userId, role);
		//下面为了兼容，返回老接口类型
		SwitchRoleResultBean resultBean = new SwitchRoleResultBean();
		resultBean.setTryFlag("-1");
		return ResultUtils.rtSuccess(resultBean);
	}
	/**
	 * 试用角色
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/tryRole")
	public Map<String, Object> tryRole(@RequestBody UserFamilyRoleLogParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		this.gameService.tryRole(bean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 查询明日角色
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/findTomorrowRole")
	public Map<String, Object> findTomorrowRole(@RequestBody(required = false) UserFamilyRoleLogParamBean bean)
	{
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("flag", "0");
		if(bean==null) {
			return ResultUtils.rtSuccess(resultMap);
		}
		int role = this.gameService.findTomorrowRole(bean);
		if (role > 0)
		{
			resultMap.put("flag", String.valueOf(1));
			resultMap.put("role", String.valueOf(role));
		}
		 
		return ResultUtils.rtSuccess(resultMap);
	}
	
	/**
	 * 查询今日角色
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/findTodayRole")
	public Map<String, Object> findTodayRole(@RequestBody UserFamilyRoleLogParamBean bean)
	{
//		LOG.info("param==={}", JSON.toJSONString(bean));
		UserRoleLog userRoleLog=userRoleLogService.getLatestUserRoleLog(this.getUserId());
//		int role = this.gameService.findTodayRole(bean);
		Map<String, Object> resultMap = new HashMap<>();
		if(userRoleLog!=null) {
			resultMap.put("role", userRoleLog.getRole());
		}else {
			resultMap.put("role", RoleConstant.LAOSIJI_ROLE);
		}
		return ResultUtils.rtSuccess(resultMap);
	}
	
	/**
	 * 装备道具
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/getReadyLottery")
	public Map<String, Object> getReadyLottery(@RequestBody ReadyLotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ReadyLotteryResultBean> resultList = null;
		try
		{
			resultList = this.gameService.getReadyLottery(bean);
		} catch (LotteryNotEnoughException e)
		{
			return ResultUtils.rtFail(null, "道具不够", "-3");
		}
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 移除道具
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/dropReadyLottery")
	public Map<String, Object> dropReadyLottery(@RequestBody ReadyLotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		this.gameService.dropReadyLottery(bean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 替换道具
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/switchLottery")
	public Map<String, Object> switchLottery(@RequestBody SwitchLotteryParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ReadyLotteryResultBean> resultList = null;
		try
		{
			resultList = this.gameService.switchLottery(bean);
		} catch (LotteryNotEnoughException e)
		{
			return ResultUtils.rtFail(null, "道具不够", "-3");
		}
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 查询选择角色明日已装备装备
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/findTomorrowReadyLottery")
	public Map<String, Object> findTomorrowReadyLottery(@RequestBody ReadyLotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ReadyLotteryResultBean> resultList = this.gameService.findTomorrowReadyLottery(bean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 增加装备道具
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/increReadyLottery")
	public Map<String, Object> increReadyLottery(@RequestBody ReadyLotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ReadyLotteryResultBean> resultList = null;
		try
		{
			resultList = this.gameService.increReadyLottery(bean);
		} catch (LotteryNotEnoughException e)
		{
			return ResultUtils.rtFail(null, "道具不够", "-3");
		}
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 减少装备道具
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/decreReadyLottery")
	public Map<String, Object> decreReadyLottery(@RequestBody ReadyLotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<ReadyLotteryResultBean> resultList = null;
		try
		{
			resultList = this.gameService.decreReadyLottery(bean);
		} catch (LotteryNotEnoughException e)
		{
			return ResultUtils.rtFailParam(null, "装备道具数量为0");
		}
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 查询用户使用角色占比
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/queryRolePercent")
	public Map<String, Object> queryRolePercent(@RequestBody UserFamilyRoleLogParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<RoleCountResultBean> resultList = this.gameService.queryRolePercent(bean);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 保存评论
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/saveReview")
	public Map<String, Object> saveReview(@RequestBody ReviewParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		this.gameService.saveReview(bean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 查询评论
	 * @param bean
	 * @return
	 */
	@RequestMapping("/game/queryReview")
	public Map<String, Object> queryReview(@RequestBody ReviewParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		ReviewResultBean resultBean = this.gameService.queryReview(bean);
		return ResultUtils.rtSuccess(resultBean);
	}
}
