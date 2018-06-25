package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.DicFamilyTypeConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.FightService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
@RestController
public class FightController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FightController.class);
	@Autowired
    FightService fightService;
	
	/**
	 * 获取明日挑战对象
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/v2/game/judgeChallengeFlag")
	public Map<String, Object> judgeChallengeFlag(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("famiyId=================" + requestBodyParams.get("famiyId"));
		Long familyId=Long.valueOf(requestBodyParams.get("famiyId").toString());
		String tomorrow=DateTools.getTomorrowDateStr();
		Long opponentId=fightService.getOpponentIdBySelfId(familyId,tomorrow);
		Map<String,String> rtMap=new HashMap<String,String>();
		if(opponentId==null){
			rtMap.put("challengeFlag", "0");
			rtMap.put("familyId", "");
			rtMap.put("familyName", "");
			rtMap.put("familyType", "");
			rtMap.put("familyTypeValue", "");
			rtMap.put("trophy", "");
			rtMap.put("imgUrl", "");
			
		}else{
			Map<String,Object> family=fightService.getOpponentInfo(opponentId);
			rtMap.put("challengeFlag", "1");
			rtMap.put("familyId", String.valueOf(family.get("id")));
			rtMap.put("familyName", String.valueOf(family.get("familyName")));
			Integer familyType=Integer.valueOf(family.get("familyType").toString());
			rtMap.put("familyType", String.valueOf(family.get("familyType")));
			rtMap.put("familyTypeValue",DicFamilyTypeConstant.getDicFamilyType(familyType).getFamilyTypeValue());
			rtMap.put("trophy", String.valueOf(family.get("trophy")));
			rtMap.put("imgUrl", this.getImgBasePath()+family.get("imgUrl"));
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	/**
	 * 选择明日挑战对象
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/v2/game/challengeFamily")
	public Map<String, Object> challengeFamily(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("famiyId=================" + requestBodyParams.get("famiyId"));
		Long selfFamilyId=Long.valueOf(requestBodyParams.get("selfFamilyId").toString());
		Long competitorFamilyId=Long.valueOf(requestBodyParams.get("competitorFamilyId").toString());
		Map<String,Object> randFamily=fightService.getRandFightFamily(selfFamilyId,competitorFamilyId);
		Map<String,String> rtMap=new HashMap<String,String>();
		if(randFamily==null){
			rtMap.put("familyId", "");
			rtMap.put("familyName", "");
			rtMap.put("familyType", "");
			rtMap.put("familyTypeValue", "");
			rtMap.put("trophy", "");
			rtMap.put("imgUrl", "");
			rtMap.put("avgScore", "0");
		}else{
			rtMap.put("familyId", String.valueOf(randFamily.get("id")));
			rtMap.put("familyName", String.valueOf(randFamily.get("familyName")));
			Integer familyType=Integer.valueOf(randFamily.get("familyType").toString());
			rtMap.put("familyType", String.valueOf(randFamily.get("familyType")));
			rtMap.put("familyTypeValue",DicFamilyTypeConstant.getDicFamilyType(familyType).getFamilyTypeValue());
			rtMap.put("trophy", String.valueOf(randFamily.get("trophy")));
			rtMap.put("imgUrl", this.getImgBasePath()+randFamily.get("imgUrl"));
			Double d=fightService.getAvgThreeDayScore(Long.valueOf(randFamily.get("id").toString()));
			rtMap.put("avgScore", String.valueOf(d.doubleValue()));
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 选择明日挑战对象
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/v2/game/submitChallengeFamily")
	public Map<String, Object> submitChallengeFamily(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("famiyId=================" + requestBodyParams.get("famiyId"));
		Long selfFamilyId=Long.valueOf(requestBodyParams.get("selfFamilyId").toString());
		Long competitorFamilyId=Long.valueOf(requestBodyParams.get("competitorFamilyId").toString());
		fightService.insertFightRelation(selfFamilyId, competitorFamilyId);
		return ResultUtils.rtSuccess(null);
	}
}
