package com.idata365.app.mapper;




import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersDiamondsLogs;

public interface AssetUsersDiamondsLogsMapper {


    int insertDiamondsConsume(AssetUsersDiamondsLogs assetUsersDiamondsLogs);

    List<AssetUsersDiamondsLogs> getIndexDiamonds(@Param("userId")long userId);
    
    List<AssetUsersDiamondsLogs> getDiamondsByEffectId(@Param("effectId")long effectId);
    
}
