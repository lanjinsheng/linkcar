package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AuctionGoods;

public interface AuctionGoodMapper {

	List<AuctionGoods> listAuctionGoods();
	
	AuctionGoods findAuctionGoodById(@Param("auctionGoodsId") long auctionGoodsId);

}