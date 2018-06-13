package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AuctionUsersDiamondsLogs;

public interface AuctionUsersDiamondsLogsMapper {

	int insertDiamondsConsume(AuctionUsersDiamondsLogs auctionUsersDiamondsLogs);

	AuctionUsersDiamondsLogs getAuctionUsersDiamondsLogsById(@Param("preUserId") long preUserId, @Param("auctionGoodsId") long auctionGoodsId);
}
