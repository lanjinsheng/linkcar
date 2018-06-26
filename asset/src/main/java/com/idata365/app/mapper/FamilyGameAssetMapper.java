package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyGameAsset;

public interface FamilyGameAssetMapper {

	int insertFamilyGameAsset(FamilyGameAsset familyGameAsset);
    int  updateRewards(FamilyGameAsset familyGameAsset);
    
    FamilyGameAsset  getFamilyGameAssetsByDayFId(FamilyGameAsset familyGameAsset);

	List<FamilyGameAsset> getFamilyGameAssets(FamilyGameAsset familyGameAsset);

	List<FamilyGameAsset> getFamilyGameAssetsPre(FamilyGameAsset familyGameAsset);

	long getFamilySeasonID(@Param("familyId") long familyId, @Param("daystamp") String daystamp);
	FamilyGameAsset getFamilyGameAssetByDay(@Param("familyId") long familyId, @Param("daystamp") String daystamp);

	long getFamilyIdByEffectId(@Param("effectId") long effectId);
	// int updateTempPowerReward(@Param("uuid") String uuid);
}
