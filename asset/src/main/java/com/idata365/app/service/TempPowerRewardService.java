package com.idata365.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AssetFamiliesPowerLogs;
import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.AssetUsersPowerLogs;
import com.idata365.app.entity.TempPowerReward;
import com.idata365.app.mapper.AssetFamiliesAssetMapper;
import com.idata365.app.mapper.AssetFamiliesPowerLogsMapper;
import com.idata365.app.mapper.AssetUsersAssetMapper;
import com.idata365.app.mapper.AssetUsersDiamondsLogsMapper;
import com.idata365.app.mapper.AssetUsersPowerLogsMapper;
import com.idata365.app.mapper.TempPowerRewardMapper;
import com.idata365.app.util.RandUtils;

/**
 * 
 * @ClassName: TempPowerRewardService
 * @Description: TODO(资产随机分配彩蛋)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class TempPowerRewardService extends BaseService<TempPowerRewardService> {
	private final static Logger LOG = LoggerFactory.getLogger(TempPowerRewardService.class);
 
	@Autowired
	TempPowerRewardMapper tempPowerRewardMapper;
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
	public TempPowerRewardService() {

	}

	 
	private List<TempPowerReward> randReward(long userId){
		List<TempPowerReward> list=new ArrayList();
		int n=1;
		for(int i=0;i<9;i++) {
			TempPowerReward p=new TempPowerReward();
			p.setUserId(userId);
			p.setCarId(Long.valueOf(RandUtils.generateRand(1, 100000)));
			p.setCarType("1");
			if(n<4) {
			//获取动力
				int r=RandUtils.generateRand(1, 100);
				if(r<=85) {
					p.setPowerNum(1l);	
				}else if(r>85 && r<95) {
					p.setPowerNum(2l);
				}else {
					p.setPowerNum(3l);
				}
				n++;
			}
			p.setUuid(userId+UUID.randomUUID().toString().replaceAll("-", ""));
			list.add(p);
		}
		return list;
	}
	/**
	 * 
	    * @Title: getTempPowerReward
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @return    参数
	    * @return List<Map<String,Object>>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public List<Map<String,Object>> getTempPowerReward(long userId) {
		List<Map<String,Object>> rtList=new ArrayList<Map<String,Object>>();
		List<TempPowerReward> rewards=randReward(userId);
		tempPowerRewardMapper.batchInsertTempPowerReward(rewards);
		for(TempPowerReward r:rewards) {
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("carId", String.valueOf(r.getCarId()));
			m.put("power", String.valueOf(r.getPowerNum()));
			m.put("carType", String.valueOf(r.getCarType()));
			m.put("uuid", r.getUuid());
			rtList.add(m);
		}
		return rtList;
	}
	/**
	 * 
	    * @Title: hadGet
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param uuid
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean hadGet(String uuid) {
		int hadGet=tempPowerRewardMapper.updateTempPowerReward(uuid);
		if(hadGet>0) {
			//
			TempPowerReward r=tempPowerRewardMapper.getTempPowerReward(uuid);
			AssetUsersPowerLogs assetUsersPowerLogs=new AssetUsersPowerLogs();
			assetUsersPowerLogs.setEventType(AssetService.EventType_Power_Index_Get);
			assetUsersPowerLogs.setEffectId(r.getId());
			assetUsersPowerLogs.setPowerNum(r.getPowerNum());
			assetUsersPowerLogs.setRecordType(AssetService.RecordType_1);
			assetUsersPowerLogs.setRemark("首页拾取增加动力");
			assetUsersPowerLogs.setUserId(r.getUserId());
			addUserPowers(assetUsersPowerLogs);
			return true;
		}else {
			return false;
		}
	}
	private boolean addUserPowers(AssetUsersPowerLogs assetUsersPowerLogs) {
		assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);
		assetUsersAssetMapper.updatePowerAdd(assetUsersPowerLogs);
		return true;
	}
	public static void main(String []args) {
		System.out.println(2+UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
