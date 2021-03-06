package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AuctionGoods;

public interface AuctionGoodMapper {

	List<AuctionGoods> listAuctionGoods();

	AuctionGoods findAuctionGoodById(@Param("auctionGoodsId") long auctionGoodsId);

	AuctionGoods findRecentAuctionGoodByTag(@Param("auctionTag") String auctionTag);

	int insertAuctionGoods(AuctionGoods auctionGoods);

	int updateAuctionGoods(AuctionGoods auctionGoods);

	int updateGoodsStatus(@Param("auctionGoodsId") long auctionGoodsId, @Param("auctionStatus") int auctionStatus);

	void lockAuctionTask(AuctionGoods auctionGoods);

	List<AuctionGoods> getAuctionTask(AuctionGoods auctionGoods);

	void updateAuctionSuccTask(AuctionGoods auctionGoods);

	void updateAuctionFailTask(AuctionGoods auctionGoods);

	void clearLockTask(@Param("compareTimes") Long compareTimes);

	int updateGoodsStatusByTask(AuctionGoods auctionGood);

	int updateGoodsByTask(AuctionGoods auctionGood);
	
	List<Map<String, Object>> getChartInfoByTag(@Param("auctionTag") String auctionTag);
	
	List<Map<String, Object>> getChartInfoByTag10Time(@Param("auctionTag") String auctionTag);

	int updateGoodsRemark(@Param("auctionGoodsId") long auctionGoodsId, @Param("remark") String remark);
}