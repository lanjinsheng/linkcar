package com.idata365.app.controller.securityV2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.FamilyService;
import com.idata365.app.serviceV2.ComponentService;
import com.idata365.app.util.ResultUtils;

@RestController
public class ComponentController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(ComponentController.class);
	@Autowired
	ComponentService componentService;
	
	@Autowired
	FamilyService familyService;
	
	@RequestMapping(value = "/getUserComponent")
	Map<String, Object> getUserComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Map<String,Object> rtMap=componentService.getUserComponent(this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	
	@RequestMapping(value = "/getFamilyComponent")
	Map<String, Object> getFamilyComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		Map<String,Object> rtMap=componentService.getFamilyComponent(familyId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	@RequestMapping(value = "/dropFamilyComponent")
	Map<String, Object> dropFamilyComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long familyComponentId=Long.valueOf(requestBodyParams.get("familyComponentId").toString());
		Map<String,Object> rtMap=componentService.dropFamilyComponent(familyComponentId);
		if(rtMap==null){
			return ResultUtils.rtFailParam(null, "零件无法丢弃");
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	//零件分配成员列表
	@RequestMapping(value = "/getComponentDistribute")
	Map<String, Object> getComponentDistribute(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Map<String,Object> family=familyService.findFamilyIdByUserId(this.getUserId());
		long familyId=Long.valueOf(family.get("id").toString());
		Map<String,Object> rtMap=componentService.getComponentDistribute(this.getUserId(),familyId);
		return ResultUtils.rtSuccess(rtMap);
	}
	//分配零件
	@RequestMapping(value = "/submitComponentDistribute")
	Map<String, Object> submitDistribute(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long familyComponentId=Long.valueOf(requestBodyParams.get("familyComponentId").toString());
		long operationManId=this.getUserId();
		Map<String,Object> family=familyService.findFamilyIdByUserId(operationManId);
		long familyId=Long.valueOf(family.get("id").toString());
		long toUserId=Long.valueOf(requestBodyParams.get("toUserId").toString());
		 int update=componentService.submitComponentDistribute(familyComponentId,familyId,toUserId,this.getUserId());
		if(update==0){
			return ResultUtils.rtFailParam(null, "赠送失败");
		}
		return ResultUtils.rtSuccess(null);
	}
	
	//获取申请日志
	@RequestMapping(value = "/getComponentGiveLog")
	Map<String, Object> getComponentGiveLog(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
		Map<String,Object> rtMap=componentService.getComponentGiveLog(componentGiveLogId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	//提交
	@RequestMapping(value = "/recieveGiveLog")
	Map<String, Object> recieveGiveLog(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
		Map<String,Object> rtMap=componentService.recieveGiveLog(componentGiveLogId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	//上架装备
	@RequestMapping(value = "/deployComponent")
	Map<String, Object> deployComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long userComponentId=Long.valueOf(requestBodyParams.get("userComponentId").toString());
		long userCarId=Long.valueOf(requestBodyParams.get("userCarId").toString());
		long destroyComponentId=0;
		Map<String,Object> rtMap=componentService.deployComponent(userComponentId,userCarId,destroyComponentId);
		return ResultUtils.rtSuccess(rtMap);
	}
		
}
