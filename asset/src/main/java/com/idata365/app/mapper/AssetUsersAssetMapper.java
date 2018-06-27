package com.idata365.app.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.AssetUsersPowerLogs;

public interface AssetUsersAssetMapper {

	AssetUsersAsset getUserAssetByUserId(@Param("userId") Long userId);

	int updateDiamondsConsume(Map<String, Object> datas);
	
	int updateDiamondsFreeze(Map<String, Object> datas);
	int updateDiamondsUnfreeze(Map<String, Object> earn);
	int updateDiamondsEarn(Map<String, Object> earn);
	int updateEndUnfreeze(Map<String, Object> map);
	
	int updatePowerAdd(AssetUsersPowerLogs assetUsersPowerLogs);
	int updatePowerReduce(AssetUsersPowerLogs assetUsersPowerLogs);

	int updateDiamondsAdd(AssetUsersDiamondsLogs assetUsersDiamondsLogs);

	int initUser(AssetUsersAsset assetUsersAsset);

	void userPowersSnapShot(@Param("tableName") String tableName);
	int clearPowers(AssetUsersAsset assetUsersAsset);

	List<AssetUsersAsset> billBoardByPower();

	List<AssetUsersAsset> billBoardByDiamond();

	int getDiamondsCurOrder(@Param("userId")long userId);
	
	int getPowersCurOrder(@Param("userId")long userId);
	
	int queryDiamondsUserOrderByDiamondsNum(@Param("diamondsNum")BigDecimal diamondsNum);
	
	int queryPowersUserOrderByPowerNum(@Param("powerNum")long powerNum);
	AssetUsersAsset getYestodayPower(Map<String,Object> map);
	long getAllYestodayAppPowers(@Param("powerTable")String powerTable);
	long getAllAppPowers();
	
	BigDecimal getAllAppDiamonds();
}
