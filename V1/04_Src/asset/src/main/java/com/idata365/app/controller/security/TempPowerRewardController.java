package com.idata365.app.controller.security;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.idata365.app.service.TempPowerRewardService;
import com.idata365.app.util.ResultUtils;

@RestController
public class TempPowerRewardController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(TempPowerRewardController.class);
	@Autowired
	TempPowerRewardService tempPowerRewardService;

	 /**
	  * 
	     * @Title: sendPower
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param @param allRequestParams
	     * @param @param requestBodyParams
	     * @param @return    参数
	     * @return Map<String,Object>    返回类型
	     * @throws
	     * @author LanYeYe
	  */
	@RequestMapping(value = "/sendPower")
	Map<String, Object> sendPower(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		return ResultUtils.rtSuccess(tempPowerRewardService.getTempPowerReward(this.getUserId()));
	}
	@RequestMapping(value = "/recPower")
	Map<String, Object> recPower(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		String uuid=String.valueOf(requestBodyParams.get("uuid"));
		if(this.tempPowerRewardService.hadGet(uuid)) {
			return ResultUtils.rtSuccess(null);
		}else {
			return ResultUtils.rtFailParam(null);
		}
	}
	 
	 
}
