package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersPowerLogs;

public interface AssetUsersPowerLogsMapper {

	int insertUsersPowerLogs(AssetUsersPowerLogs assetUsersPowerLogs);
	int insertUsersPowerLogsByTime(AssetUsersPowerLogs assetUsersPowerLogs);
	
	List<AssetUsersPowerLogs> getIndexPowers(@Param("userId") long userId, @Param("id") long id);

	List<AssetUsersPowerLogs> getIndexPowersFirst(@Param("userId") long userId);

	List<AssetUsersPowerLogs> getAllPowersByOne(@Param("userId") long userId);

	AssetUsersPowerLogs getUsersPowerLogsByEffectId(AssetUsersPowerLogs assetUsersPowerLogs);

	int getSignInLogByUserId(@Param("userId") long userId);

	List<Long> getPowersByEffectId(@Param("ballId") long ballId);

	List<AssetUsersPowerLogs> getRecordByEffectId(@Param("effectId") long effectId);
	
	int queryCountOfSteal(@Param("userId") long userId);
	
}
