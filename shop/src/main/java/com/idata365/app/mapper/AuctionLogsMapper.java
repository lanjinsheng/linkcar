package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AuctionLogs;

public interface AuctionLogsMapper {

	List<AuctionLogs> myListAuctionGoods(@Param("auctionUserId") long auctionUserId);

	int joinPersons(@Param("auctionGoodsId")long auctionGoodsId);
	
	int joinTimes(@Param("auctionGoodsId")long auctionGoodsId);
	
	int myJoinTimes(@Param("auctionGoodsId")long auctionGoodsId,@Param("userId")long userId);

	List<AuctionLogs> listAuctionGoodsRecord(@Param("auctionGoodsId") Long auctionGoodsId);

	int insertAuctionLogs(AuctionLogs auctionLogs);
	
	AuctionLogs getMaxAuctionDiamond(@Param("auctionGoodsId")long auctionGoodsId);
	
	List<AuctionLogs> listAllAuctionUsers(@Param("auctionGoodsId") Long auctionGoodsId);

	List<Long> listUserId(@Param("auctionGoodsId") Long auctionGoodsId);

	AuctionLogs getLogByLogId(@Param("auctionLogsId")long auctionLogsId);
}