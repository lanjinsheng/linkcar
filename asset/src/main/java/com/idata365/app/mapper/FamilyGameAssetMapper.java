package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.TempPowerReward;

public interface FamilyGameAssetMapper {

	int insertFamilyGameAsset(FamilyGameAsset familyGameAsset);

	List<FamilyGameAsset> getFamilyGameAssets(FamilyGameAsset familyGameAsset);

	List<FamilyGameAsset> getFamilyGameAssetsPre(FamilyGameAsset familyGameAsset);

//	long getFamilySeasonID(@Param("familyId") long familyId, @Param("daystamp") String daystamp);

	// int updateTempPowerReward(@Param("uuid") String uuid);
}
