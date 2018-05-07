package com.idata365.app.controller.security;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.LotteryConstant;
import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryMigrateInfoAllResultBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgParamBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgResultBean;
import com.idata365.app.entity.LotteryResultBean;
import com.idata365.app.entity.LotteryResultUser;
import com.idata365.app.entity.UserTravelLottery;
import com.idata365.app.service.LotteryService;
import com.idata365.app.util.ResultUtils;

@RestController
public class LotteryController extends BaseController
{
	@Autowired
	private LotteryService lotteryService;
	
	@RequestMapping("/om/listLottery")
	public Map<String, Object> listLottery(@RequestBody LotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
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
	public Map<String, Object> doLottery(@RequestBody LotteryBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		LotteryResultBean lotteryResultBean = this.lotteryService.doLottery(bean);
		return ResultUtils.rtSuccess(lotteryResultBean);
	}
	
	@RequestMapping("/om/givenLottery")
	public Map<String, Object> givenLottery(@RequestBody LotteryMigrateInfoMsgBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		
		int awardId = bean.getAwardId();
		if (LotteryConstant.MAZHA_LOTTERY == awardId
				|| LotteryConstant.ZHITIAO_LOTTERY == awardId)
		{
			return ResultUtils.rtFailParam(null, "马扎和纸条不能赠送");
		}
		
		int result = this.lotteryService.givenLottery(bean);
		if (-1 == result)
		{
			return ResultUtils.rtFail(null, "道具不够", "-3");
		}
		else
		{
			return ResultUtils.rtSuccess(null);
		}
	}
	
	@RequestMapping("/om/listFriendList")
	public Map<String, Object> listFriendList(@RequestBody LotteryMigrateInfoMsgParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		LotteryMigrateInfoAllResultBean listFriendList = this.lotteryService.listFriendList(bean);
		
		String imgBasePath = super.getImgBasePath();
		List<LotteryMigrateInfoMsgResultBean> lotteryUserList = listFriendList.getGivenLottery();
		if (CollectionUtils.isNotEmpty(lotteryUserList))
		{
			for (LotteryMigrateInfoMsgResultBean tempBean : lotteryUserList)
			{
				String imgUrl = tempBean.getImgUrl();
				if (StringUtils.isNotBlank(imgUrl))
				{
					tempBean.setImgUrl(imgBasePath + imgUrl);
				}
			}
		}
		
		return ResultUtils.rtSuccess(listFriendList);
	}
	
	@RequestMapping("/om/findUserList")
	public Map<String, Object> findUserList(@RequestBody LotteryMigrateInfoMsgParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		long tempUserId = 0;
		Long userId = bean.getUserId();
		if (userId != 0)
		{
			tempUserId = userId;
		}
		else
		{
			tempUserId = super.getUserId();
		}
		
		List<LotteryResultUser> userList = this.lotteryService.findUserList(tempUserId);
		String imgBasePath = super.getImgBasePath();
		for (LotteryResultUser tempBean : userList)
		{
			String imgUrl = tempBean.getImgUrl();
			if (StringUtils.isNotBlank(imgUrl))
			{
				tempBean.setImgUrl(imgBasePath + imgUrl);
			}
		}
		
		return ResultUtils.rtSuccess(userList);
	}
	
	@RequestMapping("/om/receiveLottery")
	public Map<String, Object> receiveLottery(@RequestBody LotteryMigrateInfoMsgParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		this.lotteryService.receiveLottery(bean);;
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping("/om/receiveTravelLottery")
	public Map<String, Object> receiveTravelLottery(@RequestBody UserTravelLottery bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		this.lotteryService.receiveTravelLottery(bean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 
	    * @Title: getChest
	    * @Description: TODO(v1.1获取宝箱道具)
	    * @param @param bean
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	
	@RequestMapping("/om/getChest")
	public Map<String, Object> getChestgetUserConfig(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) 
	{
		Map<String,Object> rtMap=lotteryService.getChest(this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	@RequestMapping("/om/recChest")
	public Map<String, Object> recChest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams) 
	{
		String id=String.valueOf(requestBodyParams.get("id"));
		 boolean bl=lotteryService.recChest(id,this.getUserId());
		return ResultUtils.rtSuccess(null);
	}
	
}
