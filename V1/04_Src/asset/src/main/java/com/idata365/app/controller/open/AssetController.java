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
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.service.AssetService;
import com.idata365.app.service.FamilyGameAssetService;
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
    * @param @param effectId
    * @param @param sign
    * @param @return    参数
    * @return String    返回类型
    * @throws
    * @author LanYeYe
 */
	@RequestMapping(value = "/asset/getUserPowerByEffectId", method = RequestMethod.POST)
	String getUserPowerByEffectId(@RequestParam(value = "effectId") long effectId,
			@RequestParam(value = "sign") String sign) {
		LOG.info("effectId:" + effectId + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(String.valueOf(effectId)));
		return assetService.getUserPowerByEffectId(effectId);
	}

	
	@RequestMapping(value = "/asset/initUserCreate", method = RequestMethod.POST)
	boolean initUserCreate(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "sign") String sign) {
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
        * @Description: TODO(对order进行sign)
        * @param @param sign
        * @param @param assetFamiliesPowerLogs
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/asset/addFamilyGameOrder",method = RequestMethod.POST)
    boolean addFamilyGameOrder(@RequestParam(value="sign")   String sign, @RequestBody   FamilyGameAsset familyGameAsset) {
    	LOG.info("familyGameAsset:" + familyGameAsset.getOrderNo() + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + SignUtils.encryptHMAC(String.valueOf(familyGameAsset.getOrderNo())));
		familyGameAssetService.insertgameAsset(familyGameAsset);
		return true;
    }
    /**
     * 
        * @Title: addFamilyGameOrderEnd
        * @Description: TODO(对season进行sign)
        * @param @param sign
        * @param @param season
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/asset/addFamilyGameOrderEnd",method = RequestMethod.POST)
    boolean addFamilyGameOrderEnd(@RequestParam(value="season")   String season,@RequestParam(value="sign")   String sign) {
    	LOG.info("addFamilyGameOrderEnd:" + season + "===sign:" + sign);
		LOG.info("校验逻辑待处理·~~~sign:" + sign);
		taskAutoAddService.syncFamilyGameEndAdd(season);
    	return true;
    }

	
	public static void main(String[] args) {
		System.out.println("Share".equals(PowerEnum.Share));
	}
}
