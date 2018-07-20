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
import com.idata365.app.util.ResultUtils;

@RestController
public class BoxTreasureController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(BoxTreasureController.class);
	@Autowired
	BoxTreasureService boxTreasureService;
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
		boolean  rt=boxTreasureService.receiveUserBox(boxId,this.getUserId());
		if(!rt){
			return ResultUtils.rtFailParam(null, "配件箱已满，请清理");
		}
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping(value = "/getChallengeBox")
	Map<String, Object> getChallengeBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long userId=this.getUserId();
		Map<String,Object> data=boxTreasureService.getChallengeBox(userId);
		return ResultUtils.rtSuccess(data);
	}
	
	@RequestMapping(value = "/receiveFamilyBox")
	Map<String, Object> receiveFamilyBox(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		String boxId=String.valueOf(requestBodyParams.get("boxId"));
		Map<String,Object> family=familyService.findFamilyIdByUserId(this.getUserId());
		boolean rt=boxTreasureService.receiveFamilyBox(boxId,Long.valueOf(family.get("id").toString()));
		if(!rt){
			return ResultUtils.rtFailParam(null, "零件库已满，请清理");
		}
		return ResultUtils.rtSuccess(null);
	}
}
