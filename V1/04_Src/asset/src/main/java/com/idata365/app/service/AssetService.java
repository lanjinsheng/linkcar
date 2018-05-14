package com.idata365.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
/**
 * 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AssetFamiliesAsset;
import com.idata365.app.entity.AssetFamiliesPowerLogs;
import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.AssetUsersPowerLogs;
import com.idata365.app.entity.StealPower;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesPowerLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersPowerLogsMapper;
import com.idata365.app.mapper.StealPowerMapper;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ValidTools;

/**
 * 
 * @ClassName: AssetService
 * @Description: TODO(资产处理)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class AssetService extends BaseService<AssetService> {
	private final static Logger LOG = LoggerFactory.getLogger(AssetService.class);
	public final static int EventType_Buy = 3;
	public final static int EventType_Power_Index_Get = 2;// 首页拾取
	public final static int EventType_Power_Trip = 4;// 行程
	public final static int EventType_Power_SignIn = 1;// 签到

	public final static int EventType_Daimond_DayPower_User = 1;// 每日分配
	public final static int EventType_Daimond_GameEnd_User = 2;// 比赛结束家族分配

	public final static int EventType_Daimond_GameEnd = 1;// 比赛获取
	public final static int EventType_Daimond_Distr = 2;// 比赛分配消耗

	public final static int RecordType_2 = 2;// 减少
	public final static int RecordType_1 = 1;// 增加
	@Autowired
	AssetUsersAssetMapper assetUsersAssetMapper;
	@Autowired
	AssetUsersDiamondsLogsMapper assetUsersDiamondsLogsMapper;
	@Autowired
	AssetUsersPowerLogsMapper assetUsersPowerLogsMapper;
	@Autowired
	AssetFamiliesPowerLogsMapper assetFamiliesPowerLogsMapper;
	@Autowired
	AssetFamiliesAssetMapper assetFamiliesAssetMapper;
	@Autowired
	StealPowerMapper stealPowerMapper;

	public AssetService() {

	}

	/**
	 * 
	 * @Title: getUserAssetByUserId
	 * @Description: TODO(通过userId获取资产)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return AssetUsersAsset 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@Transactional
	public AssetUsersAsset getUserAssetByUserId(long userId) {

		return assetUsersAssetMapper.getUserAssetByUserId(userId);
	}

	/**
	 * 
	 * @Title: getTotalNums
	 * @Description: TODO(返回首页钻石、动力总数量)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public Map<String, String> getTotalNums(long userId) {
		Map<String, String> map = new HashMap<>();
		AssetUsersAsset usersAsset = assetUsersAssetMapper.getUserAssetByUserId(userId);
		map.put("totalDiamondsNum", usersAsset.getDiamondsNum().setScale(2, RoundingMode.HALF_UP).toString());
		map.put("totalPowersNum", String.valueOf(usersAsset.getPowerNum()));
		return map;
	}

	/**
	 * 
	 * @Title: getIndexDiamonds
	 * @Description: TODO(返回首页钻石数量)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Map<String, String>> getIndexDiamonds(long userId, Map<Object, Object> requestBodyParams) {
		List<AssetUsersDiamondsLogs> list = new ArrayList<>();

		if ("0".equals(requestBodyParams.get("id").toString())) {
			list = assetUsersDiamondsLogsMapper.getIndexDiamondsFirst(userId);
		} else {
			long id = Long.valueOf(String.valueOf(requestBodyParams.get("id")));
			list = assetUsersDiamondsLogsMapper.getIndexDiamonds(userId, id);
		}

		List<Map<String, String>> data = new ArrayList<>();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> rtMap = new HashMap<String, String>();
				rtMap.put("id", String.valueOf(list.get(i).getId()));
				rtMap.put("receiveType", String.valueOf(list.get(i).getEventType()));
				rtMap.put("num", String.valueOf(list.get(i).getDiamondsNum()));
				rtMap.put("time", String.valueOf(DateTools.formatDateYMD(list.get(i).getCreateTime())));
				data.add(rtMap);
			}
		}
		return data;
	}

	/**
	 * 
	 * @Title: getIndexPowers
	 * @Description: TODO(返回首页动力数量)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Map<String, String>> getIndexPowers(long userId, Map<Object, Object> requestBodyParams) {
		List<AssetUsersPowerLogs> list = new ArrayList<>();

		if ("0".equals(requestBodyParams.get("id").toString())) {
			list = assetUsersPowerLogsMapper.getIndexPowersFirst(userId);
		} else {
			long id = Long.valueOf(String.valueOf(requestBodyParams.get("id")));
			list = assetUsersPowerLogsMapper.getIndexPowers(userId, id);
		}

		List<Map<String, String>> data = new ArrayList<>();
		if (list.size() == 0) {
			Map<String, String> rtMap = new HashMap<String, String>();
			rtMap.put("id", "");
			rtMap.put("receiveType", "");
			rtMap.put("powerNum", "");
			rtMap.put("time", "");
			data.add(rtMap);
		} else {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> rtMap = new HashMap<String, String>();
				rtMap.put("id", String.valueOf(list.get(i).getId()));
				rtMap.put("receiveType", String.valueOf(list.get(i).getEventType()));
				rtMap.put("powerNum", String.valueOf(list.get(i).getPowerNum()));
				rtMap.put("time", String.valueOf(DateTools.formatDateYMD(list.get(i).getCreateTime())));

				data.add(rtMap);
			}
		}
		return data;
	}

	/**
	 * 
	 * @Title: updateDiamondsConsume
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            userId
	 * @param @param
	 *            diamondsNum
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@Transactional
	public boolean updateDiamondsConsume(long userId, double diamondsNum) {
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("userId", userId);
		datas.put("diamondsNum", diamondsNum);
		int hadUpdate = assetUsersAssetMapper.updateDiamondsConsume(datas);
		if (hadUpdate > 0) {
			// 钻石数量够买，则进行日志增加
			AssetUsersDiamondsLogs assetUsersDiamondsLogs = new AssetUsersDiamondsLogs();
			assetUsersDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
			assetUsersDiamondsLogs.setEffectId(0L);
			assetUsersDiamondsLogs.setEventType(EventType_Buy);
			assetUsersDiamondsLogs.setRecordType(RecordType_2);
			assetUsersDiamondsLogs.setRemark("购买消费");
			assetUsersDiamondsLogs.setUserId(userId);
			assetUsersDiamondsLogsMapper.insertDiamondsConsume(assetUsersDiamondsLogs);
			return true;
		} else {
			LOG.info("userId=" + userId + "钻石数量不够支付:" + diamondsNum);
			return false;
		}
	}

	/**
	 * 
	 * @Title: addUserPowers
	 * @Description: TODO(用户动力值增加)
	 * @param @param
	 *            assetUsersPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	@Transactional
	public boolean addUserPowers(AssetUsersPowerLogs assetUsersPowerLogs) {
		assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);
		assetUsersAssetMapper.updatePowerAdd(assetUsersPowerLogs);
		return true;
	}

	/**
	 * 
	 * @Title: addFamiliesPowers
	 * @Description: TODO(家族动力值增加)
	 * @param @param
	 *            assetFamiliesPowerLogs
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@Transactional
	public boolean addFamiliesPowers(AssetFamiliesPowerLogs assetFamiliesPowerLogs) {
		assetFamiliesPowerLogsMapper.insertFamiliesPowerLogs(assetFamiliesPowerLogs);
		assetFamiliesAssetMapper.updatePowerAdd(assetFamiliesPowerLogs);
		return true;
	}

	/**
	 * 
	 * @Title: getFamilyPowers
	 * @Description: TODO(获取家族对战动力情况)
	 * @param @param
	 *            userId
	 * @param @param
	 *            familyId
	 * @param @param
	 *            fightFamilyId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LiXing
	 */

	public Map<String, Object> getFamilyPowers(long userId, Map<String, Object> familiesInfo,
			Map<Object, Object> requestBodyParams) {
		long familyId = Long.valueOf(familiesInfo.get("familyId").toString());
		long familyUserCount = Long.valueOf(familiesInfo.get("familyUserCount").toString());
		long fightFamilyId = 0;
		long fightFamilyUserCount = 0;
		if (ValidTools.isNotBlank(familiesInfo.get("fightFamilyId"))) {
			fightFamilyId = Long.valueOf(familiesInfo.get("fightFamilyId").toString());
			fightFamilyUserCount = Long.valueOf(familiesInfo.get("fightFamilyUserCount").toString());
		}

		long todayContribution = 0L;
		long todayReceive = 0L;
		List<AssetUsersPowerLogs> powers = assetUsersPowerLogsMapper.getAllPowersByOne(userId);
		for (AssetUsersPowerLogs assetUsersPowerLogs : powers) {
			if (assetUsersPowerLogs.getEventType() == 3) {
				Long effectId = assetUsersPowerLogs.getEffectId();
				AssetFamiliesPowerLogs familiesPowerLogs = assetFamiliesPowerLogsMapper.getFamiliesPowerLogs(effectId);
				if (familiesPowerLogs.getFamilyId() == familyId || familiesPowerLogs.getFamilyId() == fightFamilyId) {
					todayReceive += assetUsersPowerLogs.getPowerNum();
				}
			} else if (assetUsersPowerLogs.getEventType() == 4) {
				todayContribution += assetUsersPowerLogs.getPowerNum();
			}
		}
		if (todayContribution % 2 == 0) {
			todayContribution = todayContribution / 2;
		} else {
			todayContribution = todayContribution / 2 + 1;
		}
		List<AssetFamiliesPowerLogs> powerList = assetFamiliesPowerLogsMapper.getFamilyPowers(familyId, fightFamilyId);
		List<Map<String, Object>> powerBalls = new ArrayList<>();
		for (AssetFamiliesPowerLogs assetFamiliesPowerLogs : powerList) {
			Map<String, Object> powerBall = new HashMap<>();
			String ballId = String.valueOf(assetFamiliesPowerLogs.getId());
			powerBall.put("ballId", ballId);
			powerBall.put("totalScore", String.valueOf(assetFamiliesPowerLogs.getRealNum()));

			// 计算本次可领能量
			long count = (familyUserCount + fightFamilyUserCount) / 2 + 1;// 总共可领取次数
			Integer realCount = assetFamiliesPowerLogs.getCount();
			Long realNum = assetFamiliesPowerLogs.getRealNum();// 当前实际总量
			Long thisScore = 0L;
			if (realCount != count - 1) {
				if (realNum >= 2) {
					thisScore = RandomUtils.nextLong(1L, (long) (realNum * 0.6));
				} else {
					thisScore = 1L;
				}
			} else {
				thisScore = realNum;
			}
			powerBall.put("thisScore", String.valueOf(thisScore));
			// 球的Id即是UserPowerLogs里的effectId，且用户eventtype=3
			List<Long> ids = assetUsersPowerLogsMapper.getPowersByEffectId(Long.valueOf(ballId));
			String[] userIdsArray = {};
			if (ValidTools.isNotBlank(ids)) {
				userIdsArray = new String[ids.size()];
				for (int i = 0; i < ids.size(); i++) {
					userIdsArray[i] = ids.get(i).toString();
				}
			}
			powerBall.put("receivelist", userIdsArray);
			String createFamilyId = String.valueOf(assetFamiliesPowerLogs.getFamilyId());
			powerBall.put("createFamilyId", createFamilyId);
			if (createFamilyId.equals(String.valueOf(familyId))) {
				powerBall.put("isMyFamily", "1");
			} else {
				powerBall.put("isMyFamily", "0");
			}
			powerBall.put("createTime",
					String.valueOf(DateTools.formatDateYMD(assetFamiliesPowerLogs.getCreateTime())));
			powerBalls.add(powerBall);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("todayContribution", String.valueOf(todayContribution));
		data.put("todayReceive", String.valueOf(todayReceive));
		data.put("powerBalls", powerBalls);
		return data;
	}

	/**
	 * 
	 * @Title: stoleFamilyFightPowers
	 * @Description: TODO(家族对战动力偷取)
	 * @param @param
	 *            ballId
	 * @param @param
	 *            powerNum
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@Transactional
	public void stoleFamilyFightPowers(long userId, Map<String, Object> familiesInfo, long ballId, long powerNum)
			throws Exception {
		long familyId = Long.valueOf(familiesInfo.get("familyId").toString());
		long fightFamilyId = 0;
		if (ValidTools.isNotBlank(familiesInfo.get("fightFamilyId"))) {
			fightFamilyId = Long.valueOf(familiesInfo.get("fightFamilyId").toString());
		}

		// 修改个人相关数据
		AssetUsersPowerLogs assetUsersPowerLogs = new AssetUsersPowerLogs();
		assetUsersPowerLogs.setUserId(userId);
		assetUsersPowerLogs.setPowerNum(powerNum);
		assetUsersPowerLogs.setEffectId(ballId);
		assetUsersPowerLogs.setCreateTime(new Date());
		assetUsersPowerLogs.setEventType(3);
		assetUsersPowerLogs.setRecordType(1);
		assetUsersPowerLogs.setRemark("");
		this.addUserPowers(assetUsersPowerLogs);

		// 修改家族相关数据
		AssetFamiliesPowerLogs assetFamiliesPowerLogs = assetFamiliesPowerLogsMapper.getFamiliesPowerLogs(ballId);
		long count = assetFamiliesPowerLogs.getCount() + 1;
		if (assetFamiliesPowerLogs.getFamilyId() == familyId) {
			assetFamiliesPowerLogsMapper.updateFamiliesPowerLogs(ballId, powerNum, count);
		} else {
			assetFamiliesAssetMapper.updateFamilyPowerAdd(familyId, powerNum);
			assetFamiliesPowerLogsMapper.updateFamiliesPowerLogs(ballId, powerNum, count);
			// 修改资产
			assetFamiliesAssetMapper.updateFamilyPowerMinus(fightFamilyId, powerNum);
		}

		// 插入日志
		StealPower steal = new StealPower();
		steal.setBallId(ballId);
		steal.setDaystamp(DateTools.getCurDateYYYYMMDD());
		steal.setFamilyId(familyId);
		steal.setPowerNum((int) powerNum);
		steal.setUserId(userId);
		steal.setRemark("");
		stealPowerMapper.insertSteal(steal);
	}

	/**
	 * 
	 * @Title: getStoleFamilyFightPowers
	 * @Description: TODO(家族动力偷取记录)
	 * @param @param
	 * 
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	@Transactional
	public List<Map<String, String>> getStoleFamilyFightPowers(Map<String, Object> familiesInfo) {
		List<Map<String, String>> result = new ArrayList<>();
		long familyId = Long.valueOf(familiesInfo.get("familyId").toString());
		long fightFamilyId = 0;
		if (ValidTools.isNotBlank(familiesInfo.get("fightFamilyId"))) {
			fightFamilyId = Long.valueOf(familiesInfo.get("fightFamilyId").toString());
		}
		LOG.info("familyId===============" + familyId);
		LOG.info("fightFamilyId===============" + fightFamilyId);
		List<AssetFamiliesPowerLogs> familyPowers = assetFamiliesPowerLogsMapper.getFamilyPowers(familyId,
				fightFamilyId);
		LOG.info("familyPowers.size()===============" + familyPowers.size());
		for (AssetFamiliesPowerLogs assetFamiliesPowerLogs : familyPowers) {
			long effectId = assetFamiliesPowerLogs.getId();
			List<AssetUsersPowerLogs> list = assetUsersPowerLogsMapper.getRecordByEffectId(effectId);
			LOG.info("effectId===============" + effectId);
			LOG.info("users.size()===============" + list.size());
			for (AssetUsersPowerLogs user : list) {
				if (ValidTools.isNotBlank(user)) {
					Map<String, String> data = new HashMap<>();
					data.put("powerNum", user.getPowerNum().toString());
					data.put("time", DateTools.formatDateD(user.getCreateTime()));
					data.put("userId", user.getUserId().toString());
					result.add(data);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @Title: getUserPowerByEffectId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param
	 *            effectId
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@Transactional
	public String getUserPowerByEffectId(long effectId) {
		AssetUsersPowerLogs assetUsersPowerLogs = new AssetUsersPowerLogs();
		assetUsersPowerLogs.setEffectId(effectId);
		assetUsersPowerLogs.setEventType(EventType_Power_Trip);
		AssetUsersPowerLogs apl = assetUsersPowerLogsMapper.getUsersPowerLogsByEffectId(assetUsersPowerLogs);
		if (apl == null)
			return "0";
		return String.valueOf(apl.getPowerNum());
	}

	/**
	 * 
	 * @Title: initFamily
	 * @Description: TODO(创建家族时候调用)
	 * @param @param
	 *            familyId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	@Transactional
	public boolean initFamily(long familyId) {
		AssetFamiliesAsset assetFamiliesAsset = new AssetFamiliesAsset();
		assetFamiliesAsset.setFamilyId(familyId);
		assetFamiliesAssetMapper.initFamily(assetFamiliesAsset);
		return true;
	}

	/**
	 * 
	 * @Title: initUser
	 * @Description: TODO(创建用户初始化)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@Transactional
	public boolean initUser(long userId) {
		AssetUsersAsset assetUsersAsset = new AssetUsersAsset();
		assetUsersAsset.setUserId(userId);
		assetUsersAssetMapper.initUser(assetUsersAsset);
		return true;
	}

	@Transactional
	public void userPowersSnapShot(String tableName) {
		assetUsersAssetMapper.userPowersSnapShot(tableName);
	}

	@Transactional
	public void powerClear() {
		assetUsersAssetMapper.clearPowers(null);
		assetFamiliesAssetMapper.clearPower(null);
	}

	/**
	 * 
	 * @Title: getDaySignInLog
	 * @Description: TODO(签到获取动力)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public int getDaySignInLog(Long userId) {
		int c = assetUsersPowerLogsMapper.getSignInLogByUserId(userId);
		if (c > 0) {
			return 0;
		} else {
			AssetUsersPowerLogs assetUsersPowerLogs = new AssetUsersPowerLogs();
			assetUsersPowerLogs.setEffectId(0l);
			assetUsersPowerLogs.setEventType(EventType_Power_SignIn);
			assetUsersPowerLogs.setPowerNum(5l);
			assetUsersPowerLogs.setRecordType(RecordType_1);
			assetUsersPowerLogs.setRemark(userId + "签到获取");
			assetUsersPowerLogs.setUserId(userId);
			assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);
			return assetUsersAssetMapper.updatePowerAdd(assetUsersPowerLogs);
		}
	}
}