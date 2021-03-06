package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import com.idata365.app.entity.v2.DicLiveness;
import com.idata365.app.serviceV2.RemindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.DicFamilyTypeConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.service.FightService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;
@RestController
public class FightController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FightController.class);
	@Autowired
	FightService fightService;
	@Autowired
	RemindService remindService;
	
	/**
	 * 获取明日挑战对象
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/v2/game/judgeChallengeFlag")
	public Map<String, Object> judgeChallengeFlag(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("familyId=================" + requestBodyParams.get("familyId"));
		Long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
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
			rtMap.put("avgScore", "0");
			rtMap.put("reducePower", "0");
			String isCanRemindBoss = remindService.isCanRemindBoss(this.getUserId(),familyId);
			rtMap.put("isCanRemindBoss", isCanRemindBoss);
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
			Double d=fightService.getAvgThreeDayScore(Long.valueOf(family.get("id").toString()));
			rtMap.put("avgScore", String.valueOf(d.doubleValue()));
			
			Map<String,Object> selfFamily=fightService.getOpponentInfo(familyId);
			String challegeTime=selfFamily.get("challegeTime").toString();
    		String []dayTimes=challegeTime.split(",");
    		int reducePower=Integer.valueOf(dayTimes[1])*2;
			rtMap.put("reducePower", String.valueOf(reducePower));
			rtMap.put("isCanRemindBoss", "0");
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
		LOG.info("familyId=================" + requestBodyParams.get("selfFamilyId"));
		Long selfFamilyId=Long.valueOf(requestBodyParams.get("selfFamilyId").toString());
		Long competitorFamilyId=Long.valueOf(requestBodyParams.get("competitorFamilyId").toString());
		Map<String,Object> randFamily=fightService.getRandFightFamily(selfFamilyId,competitorFamilyId);
		Map<String,String> rtMap=new HashMap<String,String>();
		String win = "";
		String loss = "";
		if(randFamily==null){
			rtMap.put("familyId", "");
			rtMap.put("familyName", "");
			rtMap.put("familyType", "");
			rtMap.put("familyTypeValue", "");
			rtMap.put("trophy", "");
			rtMap.put("imgUrl", "");
			rtMap.put("avgScore", "0");
			rtMap.put("reducePower", "0");
			rtMap.put("win", "");
			rtMap.put("loss", "");
			return ResultUtils.rtFailParam(rtMap, "动力不足,无法匹配到对战俱乐部!");
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
			Double selfAvgScore = fightService.getAvgThreeDayScore(selfFamilyId);
			rtMap.put("selfAvgScore", String.valueOf(selfAvgScore));
			rtMap.put("reducePower",String.valueOf(randFamily.get("reducePower")));
			//DicFamilyType type = DicFamilyTypeConstant.getDicFamilyType(familyType);
			//win = "+" + type.getWin();
			//if (type.getLoss() >= 0) {
			//	loss = "+" + type.getLoss();
			//} else {
			//	loss = "" + type.getLoss();
			//}
			Double difference = (selfAvgScore - d)/10;
			//分数补偿
			int c = difference.intValue();
			int w = 30 - c;
			if (w<5) {
				w = 5;
			} else if (w > 55) {
				w = 55;
			}
			int l = -30 - c;
			if (l<-55) {
				l = -55;
			} else if (l > -5) {
				l = -5;
			}

			rtMap.put("win", String.valueOf(w));
			rtMap.put("loss", String.valueOf(l));
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 提交明日挑战对象
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/v2/game/submitChallengeFamily")
	public Map<String, Object> submitChallengeFamily(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("famiyId=================" + requestBodyParams.get("familyId"));
		Long selfFamilyId=Long.valueOf(requestBodyParams.get("selfFamilyId").toString());
		Long competitorFamilyId=Long.valueOf(requestBodyParams.get("competitorFamilyId").toString());
		if(competitorFamilyId.longValue()==0 || selfFamilyId.longValue()==0){
			return ResultUtils.rtFailParam(null, "俱乐部入参错误。");
		}
		fightService.insertFightRelation(selfFamilyId, competitorFamilyId,this.getUserId());
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 提醒老板
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/v2/game/remindBoss")
	public Map<String, Object> remindBoss(@RequestParam(required = false) Map<String, String> allRequestParams,
													 @RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		LOG.info("userId=================" + this.getUserId());
		remindService.remindBoss(this.getUserId());
		return ResultUtils.rtSuccess(null);
	}
}
