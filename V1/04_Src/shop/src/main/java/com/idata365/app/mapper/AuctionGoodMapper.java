package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AuctionGood;

public interface AuctionGoodMapper {

	List<AuctionGood> listAuctionGoods();
	
	AuctionGood findAuctionGoodById(@Param("auctionGoodsId") long auctionGoodsId);

}