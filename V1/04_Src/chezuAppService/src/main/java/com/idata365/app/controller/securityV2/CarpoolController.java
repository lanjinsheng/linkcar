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

import com.idata365.app.constant.InteractConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.InteractLogs;
import com.idata365.app.serviceV2.CarService;
import com.idata365.app.serviceV2.InteractService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;

@RestController
public class CarpoolController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(CarpoolController.class);
	@Autowired
	CarService carService;
	@RequestMapping(value = "/getMembersCarpool")
	Map<String, Object> getMembersCarpool(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		String imgBase=this.getImgBasePath();
//		List<Map<String,Object>> rtList=carService.getFamilyMemberCarSeats(userId, imgBase);
		Map<String,Object> rtMap=carService.getFamilyMemberCarSeats(userId, imgBase);
		return ResultUtils.rtSuccess(rtMap);
	}
	@RequestMapping(value = "/getSelfCarpoolTravelBy")
	Map<String, Object> getSelfCarpoolTravelBy(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		String imgBase=this.getImgBasePath();
//		List<Map<String,Object>> rtList=carService.getFamilyMemberCarSeats(userId, imgBase);
		Map<String,Object> rtMap=carService.getSelfCarpoolTravelBy(userId, this.getUserInfo().getNickName(),imgBase);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	@RequestMapping(value = "/getSelfCarpool")
	Map<String, Object> getSelfCarpool(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		String imgBase=this.getImgBasePath();
		List<Map<String,Object>> rtList=carService.getSelfCarSeats(userId, imgBase, this.getUserInfo().getNickName());
		Map<String,Object> rtMap=new HashMap<>();
		rtMap.put("carsList", rtList);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	@RequestMapping(value = "/getCarpoolApprove")
	Map<String, Object> getCarpoolApprove(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		List<Map<String,Object>> rtList=carService.getCarpoolApprove(userId,this.getImgBasePath());
		Map<String,Object> rtMap=new HashMap<>();
		rtMap.put("approveList", rtList);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	@RequestMapping(value = "/submitCarpoolApprove")
	Map<String, Object> submitCarpoolApprove(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		//sharingId就是 userCarLogs的id
		Long sharingId=Long.valueOf(requestBodyParams.get("sharingId").toString());
		int subStatus=carService.submitCarpoolApprove(userId,sharingId);
		if(subStatus==2)
		{
			return ResultUtils.rtFailParam(null,"没有可用车辆");
		}else if(subStatus==3){
			return ResultUtils.rtFailParam(null,"你已经是顺风车司机");
		}
		 
		return ResultUtils.rtSuccess(null);
	}
	
	
	@RequestMapping(value = "/applyCarpoolApprove")
	Map<String, Object> applyCarpoolApprove(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		Long approveId=Long.valueOf(requestBodyParams.get("approveId").toString());
		boolean update=carService.applyCarpoolApprove(userId, approveId);
		if(!update)
		{
			return ResultUtils.rtFailParam(null,"座位已满,提交失败");
		}
		 
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping(value = "/rejectCarpoolApprove")
	Map<String, Object> rejectCarpoolApprove(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		Long approveId=Long.valueOf(requestBodyParams.get("approveId").toString());
		boolean update=carService.rejectCarpoolApprove(userId,approveId);
		if(!update)
		{
			return ResultUtils.rtFailParam(null,"拒绝失败");
		}
		 
		return ResultUtils.rtSuccess(null);
	}
	
	
	@RequestMapping(value = "/offCar")
	Map<String, Object> offCar(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		//sharingId 就是userCarLogs的id
		Long sharingId=Long.valueOf(requestBodyParams.get("sharingId").toString());
		boolean update=carService.offCar(userId,sharingId);
		if(!update)
		{
			return ResultUtils.rtFailParam(null,"拒绝失败");
		}
		 
		return ResultUtils.rtSuccess(null);
	}
	
	
	@RequestMapping(value = "/getCarpoolRecords")
	Map<String, Object> getCarpoolRecords(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		Object maxId=requestBodyParams.get("maxId");
		Long paramMaxId=0L;
		if(maxId==null || maxId.toString().equals("-1")){
			paramMaxId=9999999999999l;
		}else{
			paramMaxId=Long.valueOf(maxId.toString());
		}
		List<Map<String,Object>> rtList=carService.getCarpoolRecords(userId,paramMaxId);
		Map<String,Object> rtMap=new HashMap<>();
		rtMap.put("records", rtList);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	 public static void main(String []args) {
		 System.out.println(System.currentTimeMillis());
	 }
}
