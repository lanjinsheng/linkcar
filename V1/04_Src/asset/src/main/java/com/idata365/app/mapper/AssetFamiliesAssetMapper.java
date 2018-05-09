package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetFamiliesAsset;
import com.idata365.app.entity.AssetFamiliesDiamondsLogs;
import com.idata365.app.entity.AssetFamiliesPowerLogs;

public interface AssetFamiliesAssetMapper {

	int updatePowerAdd(AssetFamiliesPowerLogs AssetFamiliesAsset);

	int updateFamilyPowerAdd(@Param("familyId") long familyId, @Param("powerNum") long powerNum);

	int updateFamilyPowerMinus(@Param("familyId") long familyId, @Param("powerNum") long powerNum);

	AssetFamiliesAsset getFamiliesAssetByFamilyId(@Param("familyId") Long familyId);

	int updateDiamondsAdd(AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs);

	int updateDiamondsReduce(AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs);

	int initFamily(AssetFamiliesAsset assetFamiliesAsset);
	int clearPower(AssetFamiliesAsset assetFamiliesAsset);
}
