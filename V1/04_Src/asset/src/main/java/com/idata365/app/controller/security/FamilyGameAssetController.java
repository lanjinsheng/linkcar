package com.idata365.app.controller.security;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.FamilyGameAssetService;
import com.idata365.app.util.ResultUtils;

@RestController
public class FamilyGameAssetController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FamilyGameAssetController.class);
	@Autowired
	FamilyGameAssetService familyGameAssetService;

 /**
  * 
     * @Title: getFamilyGameAsset
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param allRequestParams
     * @param @param requestBodyParams
     * @param @return    参数
     * @return Map<String,Object>    返回类型
     * @throws
     * @author LanYeYe
  */
	@RequestMapping(value = "/getFamilyGameAsset")
	Map<String, Object> getFamilyGameAsset(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long familyId =Long.valueOf(requestBodyParams.get("familyId").toString());
		Long familySeasonId=Long.valueOf(requestBodyParams.get("familySeasonId").toString());
		List<Map<String,Object>> list=familyGameAssetService.getFamilyGameAsset(familyId,familySeasonId);
		return ResultUtils.rtSuccess(list);
	}
	/**
	 * 
	    * @Title: getFamilyDistribution
	    * @Description: TODO(砖石分配)
	    * @param @param allRequestParams
	    * @param @param requestBodyParams
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/getFamilyDistribution")
	Map<String, Object> getFamilyDistribution(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Long familyId =Long.valueOf(requestBodyParams.get("familyId").toString());
		Long familySeasonId=Long.valueOf(requestBodyParams.get("familySeasonId").toString());
		List<Map<String,Object>> list=familyGameAssetService.getFamilyDistribution(familyId,familySeasonId);
		return ResultUtils.rtSuccess(list);
	}
	
	 
}
