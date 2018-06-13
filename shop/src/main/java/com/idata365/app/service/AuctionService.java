package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.entity.bean.AuctionBean;
import com.idata365.app.mapper.AuctionGoodMapper;
import com.idata365.app.mapper.AuctionLogsMapper;
import com.idata365.app.mapper.OrderMapper;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.SignUtils;

@Service
@Transactional
public class AuctionService {
	@Autowired
	private AuctionGoodMapper auctionMapper;
	@Autowired
	private AuctionLogsMapper auctionLogsMapper;
	@Autowired
	private ChezuAssetService chezuAssetService;
	@Autowired
	private OrderMapper orderMapper;

	public AuctionGoods findOneAuctionGoodById(long auctionGoodsId) {
		return auctionMapper.findAuctionGoodById(auctionGoodsId);
	}

	public List<Map<String, String>> listAuctionGoods() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<AuctionGoods> auctionGoods = auctionMapper.listAuctionGoods();
		if (auctionGoods != null && auctionGoods.size() != 0) {
			for (AuctionGoods auctionGood : auctionGoods) {
				Map<String, String> map = new HashMap<>();
				map.put("auctionId", auctionGood.getAuctionGoodsId().toString());
				map.put("auctionName", auctionGood.getPrizeName());
				map.put("auctionDesc", auctionGood.getPrizeDesc());
				map.put("auctionImg", auctionGood.getPrizePic());
				map.put("auctionValue", auctionGood.getDoneDiamond().toString());
				map.put("joinPersons", String.valueOf(auctionLogsMapper.joinPersons(auctionGood.getAuctionGoodsId())));
				map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
				map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
				map.put("endTime", DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
				map.put("difference", auctionGood.getStepPrice().toString());
				list.add(map);
			}
		}
		return list;
	}

	public Map<String, String> findAuctionGoodById(long auctionGoodsId, long userId) {

		AuctionGoods auctionGood = auctionMapper.findAuctionGoodById(auctionGoodsId);
		Map<String, String> map = new HashMap<>();
		if (auctionGood != null) {
			map.put("auctionId", String.valueOf(auctionGoodsId));
			map.put("auctionName", auctionGood.getPrizeName());
			map.put("auctionDesc", auctionGood.getPrizeDesc());
			map.put("auctionImg", auctionGood.getPrizePic());
			map.put("auctionValue", auctionGood.getDoneDiamond().toString());
			map.put("joinPersons", String.valueOf(auctionLogsMapper.joinPersons(auctionGood.getAuctionGoodsId())));
			map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
			map.put("myJoinTimes", String.valueOf(auctionLogsMapper.myJoinTimes(auctionGoodsId, userId)));
			map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
			map.put("endTime", DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
			map.put("difference", auctionGood.getStepPrice().toString());
		}
		return map;
	}

	public int joinPersons(long auctionGoodsId) {
		return auctionLogsMapper.joinPersons(auctionGoodsId);
	}

	public int joinTimes(long auctionGoodsId) {
		return auctionLogsMapper.joinTimes(auctionGoodsId);
	}

	public List<Map<String, String>> myListAuctionGoods(Long userId) {
		List<Map<String, String>> result = new ArrayList<>();
		List<AuctionLogs> auctionLogs = auctionLogsMapper.myListAuctionGoods(userId);
		if (auctionLogs != null && auctionLogs.size() != 0) {
			for (AuctionLogs auctionLog : auctionLogs) {
				Map<String, String> map = new HashMap<>();
				AuctionGoods auctionGood = auctionMapper.findAuctionGoodById(auctionLog.getAuctionGoodsId());
				map.put("convertId", auctionLog.getAuctionLogsId().toString());
				map.put("convertTime", DateTools.formatDateYMD(auctionLog.getAuctionTime()));
				map.put("auctionId", auctionLog.getAuctionGoodsId().toString());
				map.put("auctionName", auctionGood.getPrizeName());
				map.put("prizeDesc", auctionGood.getPrizeDesc());
				map.put("auctionImg", auctionGood.getPrizePic());
				map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
				map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
				map.put("endTime", DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
				map.put("auctionValue", auctionGood.getDoneDiamond().toString());
				map.put("auctionStatus", auctionGood.getStepPrice().toString());
				result.add(map);
			}
		}
		return result;
	}

	public List<Map<String, String>> listAuctionGoodsRecord(Long auctionGoodsId) {
		List<Map<String, String>> result = new ArrayList<>();
		List<AuctionLogs> auctionLogs = auctionLogsMapper.listAuctionGoodsRecord(auctionGoodsId);
		if (auctionLogs != null && auctionLogs.size() != 0) {
			for (AuctionLogs auctionLog : auctionLogs) {
				Map<String, String> map = new HashMap<>();
				map.put("auctionUserNick", auctionLog.getAuctionUserNick().toString());
				map.put("auctionTime", DateTools.formatDateYMD(auctionLog.getAuctionTime()));
				map.put("auctionDiamond", auctionLog.getAuctionDiamond().toString());
				result.add(map);
			}
		}
		return result;
	}

	public List<AuctionLogs> listAuctionGoodsBeanRecord(Long auctionGoodsId) {
		return auctionLogsMapper.listAuctionGoodsRecord(auctionGoodsId);
	}

	public int insertAuctionGoods(AuctionGoods auctionGoods) {
		// TODO Auto-generated method stub
		return auctionMapper.insertAuctionGoods(auctionGoods);
	}

	public AuctionBean getAuctionBean(Long auctionGoodsId) {
		AuctionBean auctionBean = new AuctionBean();
		AuctionGoods goods = auctionMapper.findAuctionGoodById(auctionGoodsId);
		auctionBean.setAuctionGoods(goods);
		List<AuctionLogs> auctionLogs = auctionLogsMapper.listAuctionGoodsRecord(auctionGoodsId);
		auctionBean.setAuctionLogsList(auctionLogs);
		return auctionBean;
	}

	public void doAuction(AuctionGoods auctionGoods, AuctionLogs auctionLogs, long userId, long winnerId)
			throws Exception {
		int a = auctionMapper.updateAuctionGoods(auctionGoods);
		if (a <= 0) {
			throw new RuntimeException("修改商品失败");
		}
		int b = auctionLogsMapper.insertAuctionLogs(auctionLogs);
		if (b <= 0) {
			throw new RuntimeException("插入日志失败");
		}
		// 资产操作
		String paramSign = userId + auctionLogs.getAuctionDiamond().toString();
		String sign = SignUtils.encryptDataAes(paramSign);
		boolean flag = chezuAssetService.freezeDiamondAsset(userId, auctionLogs.getAuctionDiamond().doubleValue(), sign,
				winnerId);
		if (!flag) {
			throw new RuntimeException("交易失败");
		}
	}

	public void writeChangeInfo(Long userId, Long auctionGoodsId, String phone) {
		// 修改商品状态
		auctionMapper.updateGoodsStatus(auctionGoodsId);
		orderMapper.updateOrderPhone(userId, auctionGoodsId, phone);
	}
}
