package com.idata365.app.mapper;



import com.idata365.app.entity.AssetFamiliesAsset;
import com.idata365.app.entity.AssetFamiliesPowerLogs;

public interface AssetFamiliesAssetMapper {

    int updatePowerAdd(AssetFamiliesPowerLogs assetFamiliesPowerLogs);
    
    int initFamily(AssetFamiliesAsset assetFamiliesAsset);
}
