package com.idata365.app.controller.security;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.idata365.app.controller.BaseController;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryMigrateInfoAllResultBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgParamBean;
import com.idata365.app.entity.LotteryResultBean;
import com.idata365.app.entity.SignInResultBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.service.LotteryService;
import com.idata365.app.util.ResultUtils;

public class LotteryController extends BaseController
{
	@Autowired
	private LotteryService lotteryService;
	
	@RequestMapping("/om/listLottery")
	public Map<String, Object> listLottery(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) LotteryBean bean)
	{
		LOG.debug("param==={}", JSON.toJSONString(bean));
		List<LotteryResultBean> listLottery = this.lotteryService.listLottery(bean);
		return ResultUtils.rtSuccess(listLottery);
	}
	
	/**
	 * 抽奖获得道具
	 * @param allRequestParams
	 * @param bean
	 * @return
	 */
	@RequestMapping("/om/doLottery")
	public Map<String, Object> doLottery(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) LotteryBean bean)
	{
		LOG.debug("param==={}", JSON.toJSONString(bean));
		LotteryResultBean lotteryResultBean = this.lotteryService.doLottery(bean);
		return ResultUtils.rtSuccess(lotteryResultBean);
	}
	
	@RequestMapping("/om/givenLottery")
	public Map<String, Object> givenLottery(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) LotteryMigrateInfoMsgBean bean)
	{
		LOG.debug("param==={}", JSON.toJSONString(bean));
		this.lotteryService.givenLottery(bean);
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping("/om/listFriendList")
	public Map<String, Object> listFriendList(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) LotteryMigrateInfoMsgParamBean bean)
	{
		LOG.debug("param==={}", JSON.toJSONString(bean));
		LotteryMigrateInfoAllResultBean listFriendList = this.lotteryService.listFriendList(bean);
		return ResultUtils.rtSuccess(listFriendList);
	}
	
	@RequestMapping("/om/receiveLottery")
	public Map<String, Object> receiveLottery(@RequestParam(required = false) Map<String, String> allRequestParams, @RequestBody(required = false) LotteryMigrateInfoMsgParamBean bean)
	{
		LOG.debug("param==={}", JSON.toJSONString(bean));
		this.lotteryService.receiveLottery(bean);;
		return ResultUtils.rtSuccess(null);
	}
}
