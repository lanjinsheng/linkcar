package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AuctionLogs;

public interface AuctionLogsMapper {

	List<AuctionLogs> myListAuctionGoods(@Param("auctionUserId") long auctionUserId);

	int joinPersons(@Param("auctionGoodsId")long auctionGoodsId);
	
	int joinTimes(@Param("auctionGoodsId")long auctionGoodsId);
}