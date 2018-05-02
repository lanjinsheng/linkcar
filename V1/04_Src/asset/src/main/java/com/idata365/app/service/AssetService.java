package com.idata365.app.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
public class AssetService extends BaseService<AssetService>
{
	private final static Logger LOG = LoggerFactory.getLogger(AssetService.class);
	public final static int EventType_Buy=3;
	public final static int RecordType_2=2;//减少
	public final static int RecordType_1=2;//增加
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
	
	public AssetService()
	{ 
		
	}

	/**
	 * 
	    * @Title: getUserAssetByUserId
	    * @Description: TODO(通过userId获取资产)
	    * @param @param userId
	    * @param @return    参数
	    * @return AssetUsersAsset    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public AssetUsersAsset getUserAssetByUserId(long userId)
	{

		return assetUsersAssetMapper.getUserAssetByUserId(userId);
	}
	/**
	 * 
	    * @Title: updateDiamondsConsume
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @param diamondsNum
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean updateDiamondsConsume(long userId,double diamondsNum)
	{
		Map<String,Object> datas=new HashMap<String,Object>();
		datas.put("userId", userId);
		datas.put("diamondsNum", diamondsNum);
		int hadUpdate=assetUsersAssetMapper.updateDiamondsConsume(datas);
		if(hadUpdate>0) {
			//钻石数量够买，则进行日志增加
			AssetUsersDiamondsLogs assetUsersDiamondsLogs=new AssetUsersDiamondsLogs();
			assetUsersDiamondsLogs.setDiamondsNum(BigDecimal.valueOf(diamondsNum));
			assetUsersDiamondsLogs.setEffectId(0L);
			assetUsersDiamondsLogs.setEventType(EventType_Buy);
			assetUsersDiamondsLogs.setRecordType(RecordType_2);
			assetUsersDiamondsLogs.setRemark("购买消费");
			assetUsersDiamondsLogs.setUserId(userId);
			assetUsersDiamondsLogsMapper.insertDiamondsConsume(assetUsersDiamondsLogs);
			return true;
		}else {
			LOG.info("userId="+userId+"钻石数量不够支付:"+diamondsNum);
			return false;
		}
	}
	
	/**
	 * 
	    * @Title: addUserPowers
	    * @Description: TODO(用户动力值增加)
	    * @param @param assetUsersPowerLogs
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	
	@Transactional
	public boolean addUserPowers(AssetUsersPowerLogs assetUsersPowerLogs)
	{
		assetUsersPowerLogsMapper.insertUsersPowerLogs(assetUsersPowerLogs);
		assetUsersAssetMapper.updatePowerAdd(assetUsersPowerLogs);
		return true;
	}
	/**
	 * 
	    * @Title: addFamiliesPowers
	    * @Description: TODO(家族动力值增加)
	    * @param @param assetFamiliesPowerLogs
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public boolean addFamiliesPowers(AssetFamiliesPowerLogs assetFamiliesPowerLogs)
	{
		assetFamiliesPowerLogsMapper.insertFamiliesPowerLogs(assetFamiliesPowerLogs);
		assetFamiliesAssetMapper.updatePowerAdd(assetFamiliesPowerLogs);
		return true;
	}
}
