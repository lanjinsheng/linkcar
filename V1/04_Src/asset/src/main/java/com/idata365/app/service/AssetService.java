package com.idata365.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesPowerLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersPowerLogsMapper;

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
	public final static int EventType_Buy = 3;//购买
	public final static int EventType_Power_Index_Get = 2;//首页拾取
	public final static int EventType_Power_Trip = 4;//行程
	public final static int EventType_Daimond_DayPower_User = 1;//每日分配
	public final static int EventType_Daimond_GameEnd_User = 2;//比赛结束家族分配
	
	public final static int EventType_Daimond_GameEnd = 1;//比赛获取
	public final static int EventType_Daimond_Distr = 2;//比赛分配消耗
	
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
	@Transactional
	public List<Map<String, String>> getIndexDiamonds(long userId) {
		List<AssetUsersDiamondsLogs> list = assetUsersDiamondsLogsMapper.getIndexDiamonds(userId);

		List<Map<String, String>> data = new ArrayList<>();
		if (list.size() == 0) {
			Map<String, String> rtMap = new HashMap<String, String>();
			rtMap.put("receiveType", "");
			rtMap.put("num", "");
			rtMap.put("time", "");
			data.add(rtMap);
		} else {
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> rtMap = new HashMap<String, String>();
				rtMap.put("receiveType", String.valueOf(list.get(i).getEventType()));
				rtMap.put("num", String.valueOf(list.get(i).getDiamondsNum()));
				rtMap.put("time", String.valueOf(list.get(i).getCreateTime()));
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
	    * @Title: getUserPowerByEffectId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param effectId
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public String getUserPowerByEffectId(long effectId) {
		AssetUsersPowerLogs assetUsersPowerLogs=new AssetUsersPowerLogs();
		assetUsersPowerLogs.setEffectId(effectId);
		assetUsersPowerLogs.setEventType(EventType_Power_Trip);
		AssetUsersPowerLogs apl=assetUsersPowerLogsMapper.getUsersPowerLogsByEffectId(assetUsersPowerLogs);
		if(apl==null) return "0";
		return String.valueOf(apl.getPowerNum());
	}
	
	/**
	 * 
	    * @Title: initFamily
	    * @Description: TODO(创建家族时候调用)
	    * @param @param familyId
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	
	@Transactional
	public boolean initFamily(long familyId) {
		AssetFamiliesAsset assetFamiliesAsset=new AssetFamiliesAsset();
		assetFamiliesAsset.setFamilyId(familyId);
		assetFamiliesAssetMapper.initFamily(assetFamiliesAsset);
		return true;
	}
	/**
	 * 
	    * @Title: initUser
	    * @Description: TODO(创建用户初始化)
	    * @param @param userId
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean initUser(long userId) {
		AssetUsersAsset assetUsersAsset=new AssetUsersAsset();
		assetUsersAsset.setUserId(userId);
		assetUsersAssetMapper.initUser(assetUsersAsset);
		return true;
	}
	@Transactional
	public void userPowersSnapShot(String tableName){
		assetUsersAssetMapper.userPowersSnapShot(tableName);
	}
}
