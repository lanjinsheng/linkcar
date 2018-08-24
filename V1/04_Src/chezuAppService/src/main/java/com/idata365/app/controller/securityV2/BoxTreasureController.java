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
import com.idata365.app.serviceV2.BoxTreasureService;
import com.idata365.app.serviceV2.ComponentService;
import com.idata365.app.util.ResultUtils;

@RestController
public class BoxTreasureController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(BoxTreasureController.class);
	@Autowired
	BoxTreasureService boxTreasureService;
	@Autowired
	ComponentService componentService;
	@Autowired
	FamilyService familyService;
	@RequestMapping(value = "/getTripBox")
	Map<String, Object> getTripBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		Map<String,Object> data=boxTreasureService.getTripBox(userId);
		return ResultUtils.rtSuccess(data);    
	}
	 
	@RequestMapping(value = "/receiveUserBox")
	Map<String, Object> receiveUserBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		String boxId=String.valueOf(requestBodyParams.get("boxId"));
		int rt=boxTreasureService.receiveUserBox(boxId,this.getUserId());
		if(rt==0){
			return ResultUtils.rtFailParam(null, "配件箱已满，请清理");
		}else if(rt==-1){
			return ResultUtils.rtFailParam(null, "重复领取");
		}
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping(value = "/getChallengeBox")
	Map<String, Object> getChallengeBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		Long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		Map<String,Object> data=boxTreasureService.getChallengeBox(familyId);
		return ResultUtils.rtSuccess(data);
	}
	
	@RequestMapping(value = "/receiveFamilyBox")
	Map<String, Object> receiveFamilyBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		String boxId=String.valueOf(requestBodyParams.get("boxId"));
		Map<String,Object> family=familyService.findFamilyIdByUserId(this.getUserId());
		int rt=boxTreasureService.receiveFamilyBox(boxId,Long.valueOf(family.get("id").toString()));
		if(rt==0){
			return ResultUtils.rtFailParam(null, "零件库已满，请清理");
		}else if(rt==-1){
			return ResultUtils.rtFailParam(null, "重复领取");
		}
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping(value = "/receiveUserBoxBySys")
	Map<String, Object> receiveUserBoxBySys(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		boolean rt=boxTreasureService.receiveUserBoxBySys(this.getUserId());
		if(!rt){
			return ResultUtils.rtFailParam(null, "零件库已满，请清理");
		}
		return ResultUtils.rtSuccess(null);
	}
	
}
