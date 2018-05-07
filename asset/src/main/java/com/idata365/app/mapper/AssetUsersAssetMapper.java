package com.idata365.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersAsset;
import com.idata365.app.entity.AssetUsersDiamondsLogs;
import com.idata365.app.entity.AssetUsersPowerLogs;

public interface AssetUsersAssetMapper {

	AssetUsersAsset getUserAssetByUserId(@Param("userId") Long userId);

	int updateDiamondsConsume(Map<String, Object> datas);

	int updatePowerAdd(AssetUsersPowerLogs assetUsersPowerLogs);

	int updateDiamondsAdd(AssetUsersDiamondsLogs assetUsersDiamondsLogs);

	int initUser(AssetUsersAsset assetUsersAsset);

	void userPowersSnapShot(@Param("tableName") String tableName);

}
