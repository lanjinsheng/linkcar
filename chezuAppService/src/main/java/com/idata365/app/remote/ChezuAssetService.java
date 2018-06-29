package com.idata365.app.remote;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @ClassName: ChezuAssetService
 * @Description: TODO(远程调用更新userId 与 deviceToken)
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-asset-chezu", fallback = ChezuAssetHystric.class)
public interface ChezuAssetService {
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
			@RequestParam(value = "sign") String sign);

	/**
	 * 
	 * @Title: getUsersAssetMap
	 * @Description: TODO(通过userIds 返回动力值)
	 * @param @param
	 *            userIds
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return Map<Long,String> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/getUsersAssetMap", method = RequestMethod.POST)
	Map<Long, String> getUsersAssetMap(@RequestParam(value = "userIds") String userIds,
			@RequestParam(value = "sign") String sign);

	/**
	 * 
	 * @Title: initUserCreate
	 * @Description: TODO(用户信息初始化)
	 * @param @param
	 *            userId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/initUserCreate", method = RequestMethod.POST)
	boolean initUserCreate(@RequestParam(value = "userId") long userId, @RequestParam(value = "sign") String sign);

	/**
	 * 
	 * @Title: initFamilyCreate
	 * @Description: TODO(家族信息初始化)
	 * @param @param
	 *            familyId
	 * @param @param
	 *            sign
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping(value = "/asset/initFamilyCreate", method = RequestMethod.POST)
	boolean initFamilyCreate(@RequestParam(value = "familyId") long familyId,
			@RequestParam(value = "sign") String sign);

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
			@RequestParam(value = "userId") long userId, @RequestParam(value = "sign") String sign);

	/**
	 * 
	 * @Title: billBoard
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
			@RequestParam(value = "myFamilyId") long myFamilyId, @RequestParam(value = "sign") String sign);

	@RequestMapping(value = "/asset/queryHaveNewPower", method = RequestMethod.POST)
	String queryHaveNewPower(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "familyId") long familyId, @RequestParam(value = "sign") String sign);
	
	//任务奖励
	@RequestMapping(value = "/asset/getMissionPrize", method = RequestMethod.POST)
	boolean getMissionPrize(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "powerPrize") int powerPrize,@RequestParam(value = "missionId") int missionId, @RequestParam(value = "sign") String sign);
	
	@RequestMapping(value = "/asset/reducePowersByChallege", method = RequestMethod.POST)
	public Map<String, String> reducePowersByChallege(@RequestParam(value = "userId") long userId,@RequestParam(value = "challegeTimesToday") int challegeTimesToday,
			@RequestParam(value = "sign") String sign);

	//查询用户点小车动力次数
	@RequestMapping(value = "/asset/queryCountOfSteal", method = RequestMethod.POST)
	int queryCountOfSteal(@RequestParam(value = "userId") long userId,@RequestParam(value = "sign") String sign);

}
