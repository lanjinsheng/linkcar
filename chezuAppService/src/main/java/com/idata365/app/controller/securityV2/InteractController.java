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

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.InteractService;
import com.idata365.app.util.ResultUtils;

@RestController
public class InteractController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(InteractController.class);
	@Autowired
	InteractService tempCarService;

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
	Map<String, Object> sendPower(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		    Map<String,Object> rtMap=new HashMap<String,Object>();
		    rtMap.put("carsList", tempCarService.getTempPowerReward(this.getUserId()));
		return ResultUtils.rtSuccess(rtMap);
	}
	@RequestMapping(value = "/recPower")
	Map<String, Object> recPower(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		String uuid=String.valueOf(requestBodyParams.get("uuid"));
		String []uuids=uuid.split(",");
		for(int i=0;i<uuids.length;i++) {
			this.tempCarService.hadGet(uuids[i],this.getUserId());
		}
		return ResultUtils.rtSuccess(null);
	}
	@RequestMapping(value = "/payPeccancy")
	Map<String, Object> payPeccancy(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		String peccancyId=String.valueOf(requestBodyParams.get("peccancyId"));
		
		try{
			boolean b=this.tempCarService.payPeccancy(userId, Long.valueOf(peccancyId));
	
		    if(b==false){
		    	return ResultUtils.rtFailParam(null, "罚单不存在或已被缴纳.");
		    }
		}catch(Exception e){
			e.printStackTrace();
			return ResultUtils.rtFailParam(null, "罚单缴纳失败:动力不足。");
		}
		return ResultUtils.rtSuccess(null);
	}
	@RequestMapping(value = "/getPeccancyList")
	Map<String, Object> getPeccancyList(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=Long.valueOf(requestBodyParams.get("userId").toString());
		Map<String,Object> rtMap=new HashMap<String,Object>();
		List<Map<String,Object>> list=null;
		if(this.getUserId().longValue()==userId.longValue()){
			list=this.tempCarService.getPeccancyList(userId, 0);
		}else{
			list=this.tempCarService.getPeccancyList(userId, 1);
		}
		rtMap.put("peccancyList", list);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	@RequestMapping(value = "/stealFromGarage")
	Map<String, Object> stealFromGarage(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long carUserId=Long.valueOf(requestBodyParams.get("carUserId").toString());
		Map<String,Object> rtMap=tempCarService.stealFromGarage(this.getUserId(), carUserId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	 public static void main(String []args) {
		 System.out.println(System.currentTimeMillis());
	 }
}
