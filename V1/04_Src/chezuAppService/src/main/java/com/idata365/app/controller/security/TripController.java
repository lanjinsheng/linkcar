package com.idata365.app.controller.security;

import java.util.ArrayList;
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

import com.idata365.app.constant.TripConstant;
import com.idata365.app.entity.TaskPowerLogs;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.PowerService;
import com.idata365.app.service.TripService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class TripController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(TripController.class);
	
	@Autowired
	private TripService tripService;
	@Autowired
	ChezuAssetService chezuAssetService;
	
 /**
  * 
     * @Title: getTripByUserLatest
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param allRequestParams
     * @param @param requestBodyParams
     * @param @return    参数
     * @return Map<String,Object>    返回类型
     * @throws
     * @author LanYeYe
  */
	@RequestMapping("/getTripByUserLatest")
	public Map<String, Object> getTripByUserLatest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams)
	{
		List<Map<String, Object>> rtList=new ArrayList<Map<String, Object>>();
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("userId", this.getUserId());
		if(requestBodyParams.get("id")==null || String.valueOf(requestBodyParams.get("id")).equals("0")) {
			paramMap.put("recordNum", 1);
			paramMap.put("id", 999999999999999999L);
		}
		else {
			paramMap.put("recordNum", requestBodyParams.get("recordNum"));
			paramMap.put("id", requestBodyParams.get("id"));
		}
		List<Map<String, Object>> list=tripService.getTripByUserLatest(paramMap);
		
		if(list!=null && list.size()>0) {
			for(Map<String, Object> dbMap:list) {
				String score= dbMap.get("score")==null?"0":String.valueOf(dbMap.get("score"));
				Map<String, Object> rtMap=new HashMap<String, Object>();
				rtMap.put("travelId", dbMap.get("id"));
				rtMap.put("habitId", dbMap.get("habitId"));
				rtMap.put("time", dbMap.get("time"));
				rtMap.put("mileage", dbMap.get("mileage"));
				rtMap.put("driveTip", TripConstant.getTipByScore(Double.valueOf(score)));
				rtMap.put("dayStr", dbMap.get("endTime"));
				rtMap.put("score", dbMap.get("score"));
				String sign=SignUtils.encryptHMAC(String.valueOf(dbMap.get("id")));
				String power=chezuAssetService.getUserPowerByEffectId(Long.valueOf(String.valueOf(dbMap.get("id"))), sign);
				rtMap.put("power", power);
				if(String.valueOf(dbMap.get("hiddenFlag")).equals("1")) {
					rtMap.put("isShowMapFlag", "0");
				}else {
					rtMap.put("isShowMapFlag", "1");
				}
				rtList.add(rtMap);
			} 
		}
	   return ResultUtils.rtSuccess(rtList);
	}
	/**
	 * 
	    * @Title: getTripSpeciality
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param allRequestParams
	    * @param @param requestBodyParams
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping("/getTripSpeciality")
	public Map<String, Object> getTripSpeciality(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams)
	{
		Map<String, Object> dbMap=null;
		if(requestBodyParams==null || requestBodyParams.get("travelId")==null) {
			//取最近行程
			 dbMap=tripService.getTripByUserLatest(this.getUserId());
		}else {
			 dbMap=tripService.getTripSpeciality(Long.valueOf(requestBodyParams.get("travelId").toString()));
		}
		Map<String, Object> rtMap=new HashMap<String, Object>();
		rtMap.put("score", "0");
		rtMap.put("speedingState", "0");
		rtMap.put("driveState", "0");
		rtMap.put("creditState", "0");
		rtMap.put("steadyState", "0");
		rtMap.put("mileageState", "0");
		rtMap.put("speedingState", "0");
		rtMap.put("driveState", "0");
		if(dbMap!=null) {
			rtMap.put("score", String.valueOf(dbMap.get("score")));
		} 
	   return ResultUtils.rtSuccess(rtMap);
	}
	
}
