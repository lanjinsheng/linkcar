package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersPowerLogs;

public interface AssetUsersPowerLogsMapper {

	int insertUsersPowerLogs(AssetUsersPowerLogs assetUsersPowerLogs);

	List<AssetUsersPowerLogs> getIndexPowers(@Param("userId") long userId, @Param("id") long id);

	List<AssetUsersPowerLogs> getIndexPowersFirst(@Param("userId") long userId);
	
	List<AssetUsersPowerLogs> getAllPowersByOne(@Param("userId") long userId);
	
	List<AssetUsersPowerLogs> getAllPowers();

	List<AssetUsersPowerLogs> getAllRecord();

	AssetUsersPowerLogs getUsersPowerLogsByEffectId(AssetUsersPowerLogs assetUsersPowerLogs);
	
	int getSignInLogByUserId(@Param("userId") long userId);
}
