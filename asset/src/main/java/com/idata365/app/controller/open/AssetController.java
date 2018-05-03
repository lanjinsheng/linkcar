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
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.service.AssetService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class AssetController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(AssetController.class);
	@Autowired
	AssetService assetService;

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

	
	public static void main(String[] args) {
		System.out.println("Share".equals(PowerEnum.Share));
	}
}
