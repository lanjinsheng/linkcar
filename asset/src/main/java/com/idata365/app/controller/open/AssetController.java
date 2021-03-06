package com.idata365.app.controller.open;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.app.util.ServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.idata365.app.constant.AssetConstant;
import com.idata365.app.entity.AssetFamiliesPowerLogs;
import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersPowerLogs;
import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.FamilySeasonAsset;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.service.AssetService;
import com.idata365.app.service.FamilyGameAssetService;
import com.idata365.app.service.FamilySeasonAssetService;
import com.idata365.app.service.TaskAutoAddService;
import com.idata365.app.util.GsonUtils;
import com.idata365.app.util.SignUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AssetController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(AssetController.class);
	@Autowired
	AssetService assetService;
	@Autowired
	TaskAutoAddService taskAutoAddService;
	@Autowired
	FamilyGameAssetService familyGameAssetService;
	@Autowired
	FamilySeasonAssetService familySeasonAssetService;
	@Autowired
	ChezuAccountService chezuAccountService;

	/**
	 * 
	 * @Title: getUserAsset
	 * @Description: TODO(通过用户id获取资产)
	 * @param @param
	 *            userId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/getUserAsset", method = RequestMethod.POST)
	Map<String, Object> getUserAsset(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "sign") String sign) {
		LOG.info("PARAM:" + userId + "===" + sign);
		LOG.info("校验逻辑待处理·~~~");
		AssetUsersAsset usersAsset = assetService.getUserAssetByUserId(userId);
		Map<String, Object> rtMap = new HashMap<String, Object>();
		if (usersAsset != null) {
			rtMap.put("userId", userId);
			rtMap.put("powerNum", usersAsset.getPowerNum());
			rtMap.put("diamondsNum", usersAsset.getDiamondsNum());
		} else {
			rtMap.put("userId", userId);
			rtMap.put("powerNum", "0");
			rtMap.put("diamondsNum", "0");
		}
		return rtMap;
	}
	
	/**
	 * 
	    * @Title: getUsersAssetMap
	    * @Description: TODO(通过userIds 返回动力值)
	    * @param @param userIds
	    * @param @param sign
	    * @param @return    参数
	    * @return Map<Long,String>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping(value = "/asset/getUsersAssetMap", method = RequestMethod.POST)
	Map<Long, String> getUsersAssetMap(@RequestParam(value = "userIds") String userIds,
			@RequestParam(value = "sign") String sign) {
		LOG.info("PARAM:" + userIds + "===" + sign);
		LOG.info("校验逻辑待处理·~~~");
		Map<Long, String> rtMap = new HashMap<Long, String>();
		String []arrayUsers=userIds.split(",");
		for(int i=0;i<arrayUsers.length;i++) {
			AssetUsersAsset usersAsset = assetService.getUserAssetByUserId(Long.valueOf(arrayUsers[i]));
			rtMap.put(usersAsset.getUserId(), String.valueOf(usersAsset.getPowerNum()));
		}
		return rtMap;
	}


	/**
	 * 
	 * @Title: submitDiamondAsset
	 * @Description: TODO(diamondNum减少)
	 * @param @param
	 *            userId
	 * @param @param
	 *            diamondNum
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/submitDiamondAsset", method = RequestMethod.POST)
	boolean submitDiamondAsset(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign,@RequestParam(value = "ofUserId") long ofUserId) {
		LOG.info("PARAM:" + userId + "===" + diamondNum + "====" + sign);
		LOG.info("校验逻辑待处理·~~~");
		boolean bl = assetService.updateDiamondsConsume(userId, diamondNum,ofUserId);
		return bl;
	}
	
	/**
	 * 
	 * @Title: freezeDiamondAsset
	 * @Description: TODO(diamondNum冻结解冻)
	 * @param @param
	 *            userId
	 * @param @param
	 *            diamondNum
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/freezeDiamondAsset", method = RequestMethod.POST)
	Map<String,String> freezeDiamondAsset(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign,@RequestParam(value = "preUserId") long preUserId,@RequestParam(value = "auctionGoodsId")long auctionGoodsId) {
		LOG.info("PARAM:" + userId + "===" + diamondNum + "====" + sign);
		LOG.info("校验逻辑待处理·~~~");
		
		Map<String,String> rtMap = assetService.freezeDiamondAsset(userId, diamondNum,preUserId,auctionGoodsId);
		return rtMap;
	}

	/**
	 * 
	 * @Title: addPowerUsersTask
	 * @Description: TODO(增加个人动力值)
	 * @param @param
	 *            jsonValue
	 * @param @param
	 *            sign
	 * @param @param
	 *            assetUsersPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/addPowerUsersTask", method = RequestMethod.POST)
	boolean addPowerUsersTask(@RequestParam(value = "jsonValue") String jsonValue,
			@RequestParam(value = "sign") String sign, @RequestBody AssetUsersPowerLogs assetUsersPowerLogs) {
		LOG.info("PARAM:" + jsonValue + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(jsonValue));
		return assetService.addUserPowers(assetUsersPowerLogs);
	}
	
	  @RequestMapping(value = "/asset/addPowerInteractTask",method = RequestMethod.POST)
	  boolean addPowerInteractTask(@RequestParam(value="jsonValue")  String jsonValue,@RequestParam(value="sign")   String sign){
		  LOG.info("PARAM:" + jsonValue + "===sign:" + sign); 
			LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(jsonValue));
//			 String jsonValue="{\"userId\":%d,\"stealerId\":%d,\"type\":%d,\"powerNum\":%d,\"effectId\":%d}";
			Map<String,Object> map=GsonUtils.fromJson(jsonValue);
			
			if(Integer.valueOf(map.get("type").toString())==0) {//0系统动力车
				AssetUsersPowerLogs log=new AssetUsersPowerLogs();
				log.setEffectId(Long.valueOf(map.get("effectId").toString()));
				log.setEventType(AssetConstant.EVENTTYPE_POWER_INDEX_GET);
				log.setPowerNum(Long.valueOf(map.get("powerNum").toString()));
				log.setRecordType(AssetConstant.RECORDTYPE_1);
				log.setRemark("首页获取动力球");
				log.setUserId(Long.valueOf(map.get("stealerId").toString()));
				return assetService.addUserPowers(log);
			}else if(Integer.valueOf(map.get("type").toString())==1){//行程动力车
				AssetUsersPowerLogs log=new AssetUsersPowerLogs();
				log.setEffectId(Long.valueOf(map.get("effectId").toString()));
				log.setEventType(AssetConstant.EVENTTYPE_POWER_TRIPBE_STOLE1);
				log.setPowerNum(Long.valueOf(map.get("powerNum").toString()));
				log.setRecordType(AssetConstant.RECORDTYPE_1);
				log.setRemark("偷取行程获取动力球");
				log.setUserId(Long.valueOf(map.get("stealerId").toString()));
				assetService.addUserPowers(log);
				 
				AssetUsersPowerLogs log2=new AssetUsersPowerLogs();
				log2.setEffectId(Long.valueOf(map.get("effectId").toString()));
				log2.setEventType(AssetConstant.EVENTTYPE_POWER_TRIPBE_STOLE2);
				log2.setPowerNum(Long.valueOf(map.get("powerNum").toString()));
				log2.setRecordType(AssetConstant.RECORDTYPE_2);
				log2.setRemark("行程动力被偷取");
				log2.setUserId(Long.valueOf(map.get("userId").toString()));
				return assetService.reduceUserPowers(log2);
				 
			}
		return false;
	  }
	
 
	
	@RequestMapping(value = "/asset/reducePowersByChallege", method = RequestMethod.POST)
	public Map<String, String> reducePowersByChallege(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "challegeTimesToday") int challegeTimesToday,@RequestParam(value = "sign") String sign){
		LOG.info("PARAM:userId:" + userId + "===sign:" + sign);
		Map<String, String> map = new HashMap<>();
		AssetUsersPowerLogs power=new AssetUsersPowerLogs();
		power.setUserId(userId);
		power.setEffectId(0L);
		power.setPowerNum(Double.valueOf(Math.pow(2, challegeTimesToday)).longValue());
		power.setRecordType(AssetConstant.RECORDTYPE_2);
		power.setEventType(AssetConstant.EVENTTYPE_POWER_CHALLGE_REDUCE);
		power.setRemark("挑战俱乐部选择消耗");
		boolean b = assetService.reduceUserPowers(power);
		if(b) {
			map.put("flag", "1");
		}else {
			map.put("flag", "0");
		}
		return map;
	}
	@RequestMapping(value = "/asset/reducePowersByPeccancy", method = RequestMethod.POST)
	public Map<String, String> reducePowersByPeccancy(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "payerId") long payerId,
			@RequestParam(value = "type") int type,
			@RequestParam(value = "power") int power,
			@RequestParam(value = "effectId") long effectId,
			@RequestParam(value = "sign") String sign){
		LOG.info("PARAM:userId:" + userId + "===sign:" + sign);
		Map<String, String> map = new HashMap<>();
		  
		  boolean b=false;
		if(type==0) {//0 自己缴纳罚单
			AssetUsersPowerLogs log2=new AssetUsersPowerLogs();
			log2.setUserId(payerId);
			log2.setEffectId(effectId);
			log2.setPowerNum(Long.valueOf(power));
			log2.setRecordType(AssetConstant.RECORDTYPE_2);
			log2.setEventType(AssetConstant.EVENTTYPE_POWER_CHALLGE_REDUCE);
			log2.setRemark("缴纳罚单");
			b = assetService.reduceUserPowers(log2);
		}else if(type==1){//代付缴纳罚单
			AssetUsersPowerLogs log2=new AssetUsersPowerLogs();
			log2.setUserId(payerId);
			log2.setEffectId(effectId);
			log2.setPowerNum(Long.valueOf(power));
			log2.setRecordType(AssetConstant.RECORDTYPE_2);
			log2.setEventType(AssetConstant.EVENTTYPE_POWER_HELPPAY_TICKET);
			log2.setRemark("代缴罚单");
			b = assetService.reduceUserPowers(log2);
		}
		if(b) {
			AssetUsersPowerLogs log=new AssetUsersPowerLogs();
			log.setEffectId(effectId);
			log.setEventType(AssetConstant.EVENTTYPE_POWER_GET_TICKET);
			log.setPowerNum(Long.valueOf(power));
			log.setRecordType(AssetConstant.RECORDTYPE_1);
			log.setRemark("收取贴条罚金");
			log.setUserId(userId);
			b=assetService.addUserPowers(log);
		}
		if(b) {
			map.put("flag", "1");
		}else {
			map.put("flag", "0");
		}
		return map;
	}
	/**
	 * 
	 * @Title: addPowerFamilyTask
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            jsonValue
	 * @param @param
	 *            sign
	 * @param @param
	 *            assetFamiliesDiamondsLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/addPowerFamilyTask", method = RequestMethod.POST)
	boolean addPowerFamilyTask(@RequestParam(value = "jsonValue") String jsonValue,
			@RequestParam(value = "sign") String sign, @RequestBody AssetFamiliesPowerLogs assetFamiliesPowerLogs) {
		assetFamiliesPowerLogs.setCount(0);
		assetFamiliesPowerLogs.setRealNum(assetFamiliesPowerLogs.getPowerNum());
		LOG.info("PARAM:" + jsonValue + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(jsonValue));
		return assetService.addFamiliesPowers(assetFamiliesPowerLogs);
	}

	/**
	 * 
	 * @Title: getUserPowerByEffectId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            effectId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/getUserPowerByEffectId", method = RequestMethod.POST)
	String getUserPowerByEffectId(@RequestParam(value = "effectId") long effectId,
			@RequestParam(value = "sign") String sign) {
		LOG.info("effectId:" + effectId + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(String.valueOf(effectId)));
		return assetService.getUserPowerByEffectId(effectId);
	}

	@RequestMapping(value = "/asset/initUserCreate", method = RequestMethod.POST)
	boolean initUserCreate(@RequestParam(value = "userId") long userId, @RequestParam(value = "sign") String sign) {
		LOG.info("effectId:" + userId + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(String.valueOf(userId)));
		assetService.initUser(userId);
		return true;
	}

	@RequestMapping(value = "/asset/initFamilyCreate", method = RequestMethod.POST)
	boolean initFamilyCreate(@RequestParam(value = "familyId") long familyId,
			@RequestParam(value = "sign") String sign) {
		LOG.info("effectId:" + familyId + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(String.valueOf(familyId)));
		assetService.initFamily(familyId);
		return true;
	}

	/**
	 * 
	 * @Title: addFamilyGameOrder
	 * @Description: TODO(对familyId进行sign)
	 * @param @param
	 *            sign
	 * @param @param
	 *            assetFamiliesPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/addFamilyGameOrder", method = RequestMethod.POST)
	boolean addFamilyGameOrder(@RequestParam(value = "sign") String sign,
			@RequestBody FamilyGameAsset familyGameAsset) {
		LOG.info("familyGameAsset:" + familyGameAsset.getFamilyId() + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(String.valueOf(familyGameAsset.getFamilyId())));
		familyGameAssetService.insertgameAsset(familyGameAsset);
		return true;
	}

	/**
	 * 
	 * @Title: addFamilyGameOrderEnd
	 * @Description: TODO(对season进行sign)
	 * @param @param
	 *            sign
	 * @param @param
	 *            season
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/addFamilyGameOrderEnd", method = RequestMethod.POST)
	boolean addFamilyGameOrderEnd(@RequestParam(value = "season") String season,
			@RequestParam(value = "sign") String sign) {
		LOG.info("addFamilyGameOrderEnd:" + season + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + sign);
		taskAutoAddService.syncFamilyGameEndAdd(season);
		return true;
	}

	/**
	 * 
	 * @Title: billBoard
	 * @Description: TODO(实时榜单)
	 * @param @param
	 *            familyId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             Lixing
	 */
	@RequestMapping(value = "/asset/billBoard", method = RequestMethod.POST)
	List<Map<String, String>> billBoard(@RequestParam(value = "billBoardType") String billBoardType,
			@RequestParam(value = "userId") long userId, @RequestParam(value = "sign") String sign) {
		LOG.info("billBoard:" + billBoardType + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + sign);
		LOG.info("userId:" + userId);
		List<Map<String, String>> billList = assetService.billBoard(billBoardType, userId);
		return billList;
	}

	@RequestMapping(value = "/asset/addFamilySeason", method = RequestMethod.POST)
	boolean addFamilySeason(@RequestParam(value = "sign") String sign,
			@RequestBody FamilySeasonAsset familySeasonAsset) {
		LOG.info("addFamilySeason:" + familySeasonAsset.getFamilyId() + familySeasonAsset.getTrophy() + "===sign:"
				+ sign);
		LOG.info("校验逻辑待处理·~~~sign:"
				+ SignUtils.encryptHMAC(familySeasonAsset.getFamilyId() + "" + familySeasonAsset.getTrophy()));
		familySeasonAssetService.insertSeasonAsset(familySeasonAsset);
		return true;
	}

	@RequestMapping(value = "/asset/addFamilySeasonEnd", method = RequestMethod.POST)
	boolean addFamilySeasonEnd(@RequestParam(value = "season") String season,
			@RequestParam(value = "gameDayNum") String gameDayNum, @RequestParam(value = "sign") String sign) {
		LOG.info("addFamilySeasonEnd:" + season + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(season + gameDayNum));
		taskAutoAddService.syncFamilySeasonEndAdd(season, gameDayNum);
		return true;
	}

	/**
	 * 
	 * @Title: getFamilySeasonID
	 * @Description: TODO(获取赛季ID)
	 * @param @param
	 *            familyId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return long 返回类型
	 * @throws @author
	 *             Lixing
	 */
	@RequestMapping(value = "/asset/getFamilySeasonID", method = RequestMethod.POST)
	long getFamilySeasonID(@RequestParam(value = "daystamp") String daystamp,
			@RequestParam(value = "myFamilyId") long myFamilyId, @RequestParam(value = "sign") String sign) {
		LOG.info("myFamilyId:" + myFamilyId + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("getFamilySeasonID·~~~controller");
		long familySeasonId = familyGameAssetService.getFamilySeasonID(myFamilyId, daystamp);
		return familySeasonId;
	}
	
	//查询是否有新的动力球
	@RequestMapping(value = "/asset/queryHaveNewPower", method = RequestMethod.POST)
	String queryHaveNewPower(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "familyId") long familyId, @RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"   familyId:" + familyId + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryHaveNewPower·~~~controller");
		Map<String, Object> familiesInfo = chezuAccountService.getFamiliesInfoByfamilyId(familyId, sign);
		String haveNewPower = assetService.queryHaveNewPower(userId, familiesInfo);
		return haveNewPower == null ? "0" : haveNewPower;
	}
	
	/**
	 * 
	 * @Title: getMissionPrize
	 * @Description: TODO(任务奖励动力增加)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/getMissionPrize", method = RequestMethod.POST)
	boolean getMissionPrize(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "powerPrize") int powerPrize, @RequestParam(value = "missionId") int missionId,
			@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"   powerPrize:" + powerPrize +"   missionId:" + missionId +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("getMissionPrize·~~~controller");
		AssetUsersPowerLogs logs = new AssetUsersPowerLogs();
		logs.setUserId(userId);
		logs.setPowerNum(Long.valueOf(powerPrize));
		logs.setEffectId(Long.valueOf(missionId));
		logs.setEventType(missionId + 100);
		logs.setRecordType(AssetConstant.RECORDTYPE_1);
		logs.setRemark(AssetConstant.UserPowerEventMap.get(missionId + 100));
		return assetService.addUserPowers(logs);
	}
	
	/**
	 * 
	 * @Title: getMissionPrize
	 * @Description: TODO(查询用户首页拾取添加动力次数)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/queryCountOfSteal", method = RequestMethod.POST)
	int queryCountOfSteal(@RequestParam(value = "userId") long userId,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryCountOfSteal·~~~controller");
		int count = assetService.queryCountOfSteal(userId);
		return count;
	}

	/**
	 * 
	 * @Title: queryHadGetBonus
	 * @Description: TODO(由app调用资产工程查询用户是否已经领过俱乐部奖金)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/queryHadGetBonus", method = RequestMethod.POST)
	int queryHadGetBonus(@RequestParam(value = "userId") long userId,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryHadGetBonus·~~~controller");
		int count = assetService.queryHadGetBonus(userId);
		return count;
	}
	
	/**
	 * 
	 * @Title: receiveClubBonus
	 * @Description: TODO(领取俱乐部奖金)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/receiveClubBonus", method = RequestMethod.POST)
	boolean receiveClubBonus(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "powerNum") long powerNum,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"   powerNum:" + powerNum +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("receiveClubBonus·~~~controller");
		AssetUsersPowerLogs logs = new AssetUsersPowerLogs();
		logs.setUserId(userId);
		logs.setPowerNum(powerNum);
		logs.setEventType(AssetConstant.EVENTTYPE_POWER_CLUB_BONUS);
		logs.setRecordType(AssetConstant.RECORDTYPE_1);
		logs.setRemark("俱乐部奖金");
		return assetService.addUserPowers(logs);
	}
	
	/**
	 * 
	 * @Title: receiveDayMissionBox
	 * @Description: TODO(领取每日任务宝箱)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/receiveDayMissionBox", method = RequestMethod.POST)
	boolean receiveDayMissionBox(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "powerNum") long powerNum,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"   powerNum:" + powerNum +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("receiveDayMissionBox·~~~controller");
		AssetUsersPowerLogs logs = new AssetUsersPowerLogs();
		logs.setUserId(userId);
		logs.setPowerNum(powerNum);
		logs.setEventType(AssetConstant.EVENTTYPE_POWER_MISSION_DAYBOX);
		logs.setRecordType(AssetConstant.RECORDTYPE_1);
		logs.setRemark("每日任务宝箱领取power");
		AssetUsersPowerLogs logsLatest = assetService.getLogsLatest(userId);
		if (logsLatest!=null&&logsLatest.getEventType()==AssetConstant.EVENTTYPE_POWER_MISSION_DAYBOX&&logsLatest.getPowerNum()==powerNum) {
			return false;
		}
		return assetService.addUserPowers(logs);
	}
	
	/**
	 * 
	 * @Title: queryReceiveDayMissionBox
	 * @Description: TODO(由app调用资产工程查询用户领取每日任务宝箱次数)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/queryReceiveDayMissionBox", method = RequestMethod.POST)
	int queryReceiveDayMissionBox(@RequestParam(value = "userId") long userId,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryReceiveDayMissionBox·~~~controller");
		int count = assetService.queryReceiveDayMissionBox(userId);
		return count;
	}
	
	/**
	 * 
	 * @Title: receiveDayMissionBox
	 * @Description: TODO(领取活动任务宝箱)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/receiveActMissionBox", method = RequestMethod.POST)
	boolean receiveActMissionBox(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "powerNum") long powerNum,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"   powerNum:" + powerNum +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("receiveActMissionBox·~~~controller");
		AssetUsersPowerLogs logs = new AssetUsersPowerLogs();
		logs.setUserId(userId);
		logs.setPowerNum(powerNum);
		logs.setEventType(AssetConstant.EVENTTYPE_POWER_MISSION_ACTBOX);
		logs.setRecordType(AssetConstant.RECORDTYPE_1);
		logs.setRemark("活动任务宝箱领取power");
		AssetUsersPowerLogs logsLatest = assetService.getLogsLatest(userId);
		if (logsLatest!=null&&logsLatest.getEventType()==AssetConstant.EVENTTYPE_POWER_MISSION_ACTBOX&&logsLatest.getPowerNum()==powerNum) {
			return false;
		}
		return assetService.addUserPowers(logs);
	}
	
	/**
	 * 
	 * @Title: queryReceiveActMissionBox
	 * @Description: TODO(由app调用资产工程查询用户领取活动任务宝箱次数)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/queryReceiveActMissionBox", method = RequestMethod.POST)
	int queryReceiveActMissionBox(@RequestParam(value = "userId") long userId,@RequestParam(value = "sign") String sign) {
		LOG.info("userId:" + userId +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryReceiveDayMissionBox·~~~controller");
		int count = assetService.queryReceiveActMissionBox(userId);
		return count;
	}
	
	/**
	 * 
	 * @Title: queryMaxActPowerByTime
	 * @Description: TODO(由app调用资产工程 根据时间获取当时活动任务最高动力值)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/queryMaxActPowerByTime", method = RequestMethod.POST)
	long queryMaxActPowerByTime(@RequestParam(value = "daystamp") String daystamp,@RequestParam(value = "sign") String sign) {
		LOG.info("daystamp:" + daystamp +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryReceiveDayMissionBox·~~~controller");
		long powerNum = assetService.queryMaxActPowerByTime(daystamp);
		return powerNum;
	}
	
	/**
	 * 
	 * @Title: queryMaxActPowerByTimeAndUserId
	 * @Description: TODO(由app调用资产工程 根据时间获取当时活动任务最高动力值)
	 * @param @param
	 */
	@RequestMapping(value = "/asset/queryMaxActPowerByTimeAndUserId", method = RequestMethod.POST)
	long queryMaxActPowerByTimeAndUserId(@RequestParam(value = "daystamp") String daystamp,@RequestParam(value = "userId") long userId,@RequestParam(value = "sign") String sign) {
		LOG.info("daystamp:" + daystamp +"===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:");
		LOG.info("queryMaxActPowerByTimeAndUserId·~~~controller");
		long powerNum = assetService.queryMaxActPowerByTimeAndUserId(daystamp,userId);
		return powerNum;
	}

	@RequestMapping(value = "/ment/queryPowerStatistics", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryPagePowerStatistics(HttpServletRequest request) {
		Map<String, Object> map = this.getPagerMap(request);
		map.putAll(requestParameterToMap(request));

		List<Map<String, Object>> list = assetService.queryPowerStatistics(Integer.valueOf(map.get("day").toString()));
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(list));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	
	public static void main(String[] args) {
		System.out.println("Share".equals(PowerEnum.Share));
	}
}
