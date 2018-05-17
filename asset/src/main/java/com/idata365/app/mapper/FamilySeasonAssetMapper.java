package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.FamilySeasonAsset;
import com.idata365.app.entity.TempPowerReward;

public interface FamilySeasonAssetMapper {

	int insertFamilySeasonAsset(FamilySeasonAsset familySeasonAsset);

//	List<FamilySeasonAsset> getFamilySeasonAssets(FamilySeasonAsset gamilySeasonAsset);
//
//	List<FamilySeasonAsset> getFamilySeasonAssetsPre(FamilySeasonAsset gamilySeasonAsset);

}
