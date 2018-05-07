package com.idata365.app.mapper;



import com.idata365.app.entity.AssetFamiliesAsset;
import com.idata365.app.entity.AssetFamiliesDiamondsLogs;
import com.idata365.app.entity.AssetFamiliesPowerLogs;

public interface AssetFamiliesAssetMapper {

    int updatePowerAdd(AssetFamiliesPowerLogs assetFamiliesPowerLogs);
    int updateDiamondsAdd(AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs);
    int updateDiamondsReduce(AssetFamiliesDiamondsLogs assetFamiliesDiamondsLogs);
    
    int initFamily(AssetFamiliesAsset assetFamiliesAsset);
}
