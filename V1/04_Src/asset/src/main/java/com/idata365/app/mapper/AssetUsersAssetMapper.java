package com.idata365.app.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersAsset;

public interface AssetUsersAssetMapper {

    AssetUsersAsset getUserAssetByUserId(@Param("userId") Long userId);

    int updateDiamondsConsume(Map<String,Object> datas);

}
