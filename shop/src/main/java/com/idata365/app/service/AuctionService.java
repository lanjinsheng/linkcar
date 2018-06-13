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
import com.idata365.app.mapper.AuctionGoodMapper;
import com.idata365.app.mapper.AuctionLogsMapper;
import com.idata365.app.util.DateTools;

@Service
@Transactional
public class AuctionService {
	@Autowired
	private AuctionGoodMapper auctionMapper;
	@Autowired
	private AuctionLogsMapper auctionLogsMapper;

	public List<Map<String, String>> listAuctionGoods() {

		List<AuctionGoods> auctionGoods = auctionMapper.listAuctionGoods();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
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
			map.put("endTime",  DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
			map.put("difference",auctionGood.getStepPrice().toString());
			list.add(map);
		}
		return list;
	}

	public List<Map<String, String>> myListAuctionGoods(Long userId) {
		List<Map<String, String>> result = new ArrayList<>();
		List<AuctionLogs> auctionLogs= auctionLogsMapper.myListAuctionGoods(userId);
		if(auctionLogs!=null&&auctionLogs.size()!=0) {
			for (AuctionLogs auctionLog : auctionLogs) {
				Map<String, String> map = new HashMap<>();
				AuctionGoods auctionGood = auctionMapper.findAuctionGoodById(auctionLog.getAuctionGoodsId());
				map.put("convertId", auctionLog.getAuctionLogsId().toString());
				map.put("convertTime",  DateTools.formatDateYMD(auctionLog.getAuctionTime()));
				map.put("auctionId", auctionLog.getAuctionGoodsId().toString());
				map.put("auctionName", auctionGood.getPrizeName());
				map.put("prizeDesc", auctionGood.getPrizeDesc());
				map.put("auctionImg", auctionGood.getPrizePic());
				map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
				map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
				map.put("endTime",  DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
				map.put("auctionValue",auctionGood.getDoneDiamond().toString());
				map.put("auctionStatus",auctionGood.getStepPrice().toString());
				result.add(map);
			}
		}
		return result;
	}

	public List<AuctionLogs> listAuctionGoodsRecord(Long auctionGoodsId) {
		List<AuctionLogs> auctionLogs= auctionLogsMapper.listAuctionGoodsRecord(auctionGoodsId);
		return auctionLogs;
	}
}
