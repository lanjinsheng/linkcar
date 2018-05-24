package com.idata365.app.controller.open;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AssetFamiliesPowerLogs;
import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersPowerLogs;
import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.FamilySeasonAsset;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.service.AssetService;
import com.idata365.app.service.FamilyGameAssetService;
import com.idata365.app.service.FamilySeasonAssetService;
import com.idata365.app.service.TaskAutoAddService;
import com.idata365.app.util.SignUtils;

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
			@RequestParam(value = "diamondNum") double diamondNum, @RequestParam(value = "sign") String sign) {
		LOG.info("PARAM:" + userId + "===" + diamondNum + "====" + sign);
		LOG.info("校验逻辑待处理·~~~");
		boolean bl = assetService.updateDiamondsConsume(userId, diamondNum);
		return bl;
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

	public static void main(String[] args) {
		System.out.println("Share".equals(PowerEnum.Share));
	}
}
