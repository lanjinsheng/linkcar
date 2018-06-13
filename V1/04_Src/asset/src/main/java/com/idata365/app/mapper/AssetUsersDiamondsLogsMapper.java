package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetUsersDiamondsLogs;

public interface AssetUsersDiamondsLogsMapper {

	int insertDiamondsConsume(AssetUsersDiamondsLogs assetUsersDiamondsLogs);

	List<AssetUsersDiamondsLogs> getIndexDiamonds(@Param("userId") long userId, @Param("id") long id);

	List<AssetUsersDiamondsLogs> getIndexDiamondsFirst(@Param("userId") long userId);

	int insertUsersDiamondsDay(AssetUsersDiamondsLogs assetUsersDiamondsLogs);

	List<AssetUsersDiamondsLogs> getPkDiamondsByEffectId(@Param("effectId") long effectId);

	AssetUsersDiamondsLogs getYestodayPkDiamonds(@Param("userId") long userId, @Param("effectId") long effectId);

	AssetUsersDiamondsLogs getYestodaySeasonDiamonds(@Param("userId") long userId, @Param("effectId") long effectId);

	AssetUsersDiamondsLogs getYestodayPersonPowerDiamonds(@Param("userId") long userId);
}
