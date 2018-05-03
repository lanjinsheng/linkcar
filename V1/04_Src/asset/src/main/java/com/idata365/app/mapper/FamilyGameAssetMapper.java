package com.idata365.app.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.FamilyGameAsset;
import com.idata365.app.entity.TempPowerReward;

public interface FamilyGameAssetMapper {

//	 int batchInsertTempPowerReward(List<TempPowerReward> tempPowerReward);
	List<FamilyGameAsset>  getFamilyGameAssets(FamilyGameAsset familyGameAsset);
	List<FamilyGameAsset>  getFamilyGameAssetsPre(FamilyGameAsset familyGameAsset);
	 
//	 int updateTempPowerReward(@Param("uuid") String uuid);
}
