package com.idata365.app.mapper;

import com.idata365.app.entity.AssetUsersPowerLogs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AssetUsersPowerLogsMapper {

    int insertUsersPowerLogs(AssetUsersPowerLogs assetUsersPowerLogs);

    int insertUsersPowerLogsByTime(AssetUsersPowerLogs assetUsersPowerLogs);

    List<AssetUsersPowerLogs> getIndexPowers(@Param("userId") long userId, @Param("id") long id);

    List<AssetUsersPowerLogs> getIndexPowersFirst(@Param("userId") long userId);

    List<AssetUsersPowerLogs> getAllPowersByOne(@Param("userId") long userId);

    AssetUsersPowerLogs getUsersPowerLogsByEffectId(AssetUsersPowerLogs assetUsersPowerLogs);

    AssetUsersPowerLogs getUsersPowerLogsByUserEffectId(AssetUsersPowerLogs assetUsersPowerLogs);

    int getSignInLogByUserId(@Param("userId") long userId);

    List<Long> getPowersByEffectId(@Param("ballId") long ballId);

    List<AssetUsersPowerLogs> getRecordByEffectId(@Param("effectId") long effectId);

    int queryCountOfSteal(@Param("userId") long userId);

    int queryHadGetBonus(@Param("userId") long userId);

    int queryReceiveDayMissionBox(@Param("userId") long userId);

    int queryReceiveActMissionBox(@Param("userId") long userId);

    Long queryMaxActPowerByTime(@Param("daystamp") String daystamp);

    Long queryMaxActPowerByTimeAndUserId(@Param("daystamp") String daystamp, @Param("userId") long userId);

    List<Map<String, Object>> queryPowerStatistics(@Param("day") Integer day);

    AssetUsersPowerLogs getLogsLatest(@Param("userId") long userId);

}
