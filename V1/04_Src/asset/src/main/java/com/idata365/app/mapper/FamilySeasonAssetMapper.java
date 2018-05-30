package com.idata365.app.mapper;


import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilySeasonAsset;

public interface FamilySeasonAssetMapper {

	int insertFamilySeasonAsset(FamilySeasonAsset familySeasonAsset);
	long getFamilySeasonID(@Param("familyId") long familyId, @Param("daystamp") String daystamp);

//	List<FamilySeasonAsset> getFamilySeasonAssets(FamilySeasonAsset gamilySeasonAsset);
//
//	List<FamilySeasonAsset> getFamilySeasonAssetsPre(FamilySeasonAsset gamilySeasonAsset);

}
