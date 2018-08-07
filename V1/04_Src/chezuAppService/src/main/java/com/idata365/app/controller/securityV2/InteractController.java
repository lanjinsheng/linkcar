package com.idata365.app.controller.securityV2;

import java.util.Date;
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

import com.idata365.app.constant.InteractConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.InteractLogs;
import com.idata365.app.serviceV2.InteractService;
import com.idata365.app.serviceV2.UserInfoServiceV2;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;

@RestController
public class InteractController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(InteractController.class);
	@Autowired
	InteractService tempCarService;
	@Autowired
	UserInfoServiceV2 userInfoServiceV2;

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
			this.tempCarService.hadGet(uuids[i],this.getUserId(),this.getUserInfo().getNickName());
		}
		return ResultUtils.rtSuccess(null);
	}
	@RequestMapping(value = "/payPeccancy")
	Map<String, Object> payPeccancy(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		String peccancyId=String.valueOf(requestBodyParams.get("peccancyId"));
		
		try{
			boolean b=this.tempCarService.payPeccancy(userId, Long.valueOf(peccancyId),this.getUserInfo().getNickName());
	
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
		Map<String,Object> rtMap=tempCarService.stealFromGarage(this.getUserId(), carUserId,this.getUserInfo().getNickName());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	@RequestMapping(value = "/clickComeOn")
	Map<String, Object> clickComeOn(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long toUserId=Long.valueOf(requestBodyParams.get("toUserId").toString());
		String toUserName=(requestBodyParams.get("toUserName").toString());
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userIdA", this.getUserId());
		param.put("userIdB", toUserId);
		param.put("daystamp", DateTools.getYYYY_MM_DD());
		if(tempCarService.hadComeOn(param)>0){
			return ResultUtils.rtFailParam(null,"已经点赞过");
		}
		InteractLogs log=new InteractLogs();
		log.setEventType(InteractConstant.CLICK_COMEON);
		log.setSomeValue(1);
		log.setUserIdA(this.getUserId());
		log.setUserIdB(toUserId);
		log.setUserNameA(this.getUserInfo().getNickName());
		log.setUserNameB(toUserName);
		log.setUserCarId(0L);
		tempCarService.insertInteractLogs(log);
		return ResultUtils.rtSuccess(null);
	}
	@RequestMapping(value = "/getInteractLogs")
	Map<String, Object> getInteractLogs(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		 int tabType=Integer.valueOf(requestBodyParams.get("tabType").toString());
		 Long userId=Long.valueOf(requestBodyParams.get("userId").toString());
		 Long maxId=Long.valueOf(requestBodyParams.get("maxId").toString());
		 if(maxId==-1){
			 maxId=99999999999999L;
		 }
	    List<Map<String,Object>> rtList=tempCarService.getInteractLogs(tabType, userId, maxId);
	    Map<String,Object> rtMap=new HashMap<String,Object>();
	    rtMap.put("interactLogs", rtList);
		return ResultUtils.rtSuccess(rtMap);
	}
		
	@RequestMapping(value = "/cleanCar")
	Map<String, Object> cleanCar(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams) {
		Long toUserId = Long.valueOf(requestBodyParams.get("toUserId").toString());
		Long userCarId = Long.valueOf(requestBodyParams.get("userCarId").toString());

		Map<String, Object> m = new HashMap<>();
		m.put("userCarId", userCarId);
		Date daystamp = DateTools.getAddMinuteDateTime(new Date(), -60 * 3);
		m.put("daystamp", daystamp);
		int i = tempCarService.queryIsCanCleanCar(m);
		if (i > 0) {
			return ResultUtils.rtFailParam(null, "别人已经擦过了");
		}

		InteractLogs log = new InteractLogs();
		log.setEventType(InteractConstant.CLEAN_CAR);
		log.setSomeValue(0);
		log.setUserIdA(this.getUserId());
		log.setUserIdB(toUserId);
		log.setUserNameA(this.getUserInfo().getNickName());
		log.setUserNameB(userInfoServiceV2.getUsersAccount(toUserId).getNickName());
		log.setUserCarId(userCarId);
		tempCarService.insertInteractLogs(log);
		return ResultUtils.rtSuccess(null);
	}
	
	 public static void main(String []args) {
		 System.out.println(System.currentTimeMillis());
	 }
}
