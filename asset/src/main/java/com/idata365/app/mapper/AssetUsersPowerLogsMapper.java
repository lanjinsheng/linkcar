package com.idata365.app.mapper;



import com.idata365.app.entity.AssetUsersPowerLogs;

public interface AssetUsersPowerLogsMapper {

	 int insertUsersPowerLogs(AssetUsersPowerLogs assetUsersPowerLogs);
	 AssetUsersPowerLogs getUsersPowerLogsByEffectId(AssetUsersPowerLogs assetUsersPowerLogs);

}
