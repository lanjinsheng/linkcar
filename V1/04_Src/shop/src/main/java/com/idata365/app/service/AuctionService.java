package com.idata365.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	protected static final Logger LOG = LoggerFactory.getLogger(AuctionService.class);
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
				map.put("auctionValue", auctionGood.getDoneDiamond().stripTrailingZeros().toPlainString());
				map.put("joinPersons", String.valueOf(auctionLogsMapper.joinPersons(auctionGood.getAuctionGoodsId())));
				map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
				map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
				map.put("endTime", DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
				map.put("difference", auctionGood.getStepPrice().stripTrailingZeros().toPlainString());
				map.put("auctionGoodsType", auctionGood.getAuctionGoodsType().toString());
				map.put("isMustVerify", auctionGood.getIsMustVerify().toString());
				map.put("auctionTag", auctionGood.getAuctionTag());
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
			map.put("auctionValue", auctionGood.getDoneDiamond().stripTrailingZeros().toPlainString());
			map.put("joinPersons", String.valueOf(auctionLogsMapper.joinPersons(auctionGood.getAuctionGoodsId())));
			map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
			map.put("myJoinTimes", String.valueOf(auctionLogsMapper.myJoinTimes(auctionGoodsId, userId)));
			map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
			map.put("endTime", DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
			map.put("difference", auctionGood.getStepPrice().stripTrailingZeros().toPlainString());
			map.put("auctionGoodsType", auctionGood.getAuctionGoodsType().toString());
			map.put("isMustVerify", auctionGood.getIsMustVerify().toString());
			map.put("auctionTag", auctionGood.getAuctionTag());
			if (auctionGood.getWinnerId() == userId
					&& ((new Date().getTime() - auctionGood.getAuctionRealEndTime().getTime()) >= 0)) {
				map.put("convertStatus", map.put("convertStatus", auctionGood.getAuctionStatus().toString()));
			}else {
				map.put("convertStatus","0");
			}
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
				map.put("auctionDesc", auctionGood.getPrizeDesc());
				map.put("auctionImg", auctionGood.getPrizePic());
				map.put("joinPersons", String.valueOf(auctionLogsMapper.joinPersons(auctionGood.getAuctionGoodsId())));
				map.put("joinTimes", String.valueOf(auctionLogsMapper.joinTimes(auctionGood.getAuctionGoodsId())));
				map.put("startTime", DateTools.formatDateYMD(auctionGood.getAuctionStartTime()));
				map.put("endTime", DateTools.formatDateYMD(auctionGood.getAuctionRealEndTime()));
				map.put("auctionValue", auctionGood.getDoneDiamond().stripTrailingZeros().toPlainString());
				map.put("auctionGoodsType", auctionGood.getAuctionGoodsType().toString());
				map.put("difference", auctionGood.getStepPrice().stripTrailingZeros().toPlainString());
				map.put("isMustVerify", auctionGood.getIsMustVerify().toString());
				map.put("auctionTag", auctionGood.getAuctionTag());
				if (auctionGood.getWinnerId() == userId
						&& ((new Date().getTime() - auctionGood.getAuctionRealEndTime().getTime()) >= 0)) {
					map.put("convertStatus", auctionGood.getAuctionStatus().toString());
				}else {
					map.put("convertStatus","0");
				}
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
				map.put("nick", auctionLog.getAuctionUserNick().toString());
				map.put("auctionTime", DateTools.formatDateYMD(auctionLog.getAuctionTime()));
				map.put("auctionDiamond", auctionLog.getAuctionDiamond().stripTrailingZeros().toPlainString());
				result.add(map);
			}
		}
		return result;
	}
	
	public List<Map<String, String>> getChartInfo(Long auctionGoodsId) {
		List<Map<String, String>> result = new ArrayList<>();
		// 商品标签
		String auctionTag = this.auctionMapper.findAuctionGoodById(auctionGoodsId).getAuctionTag();
		List<Map<String, Object>> list = this.auctionMapper.getChartInfoByTag(auctionTag);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Map<String, Object> bean : list) {
				Map<String, String> map = new HashMap<>();
				map.put("abscissa", String.valueOf(bean.get("time")).substring(5,10));
				map.put("ordinate", BigDecimal.valueOf(Double.valueOf(String.valueOf(bean.get("diamondsNum"))))
						.setScale(1, RoundingMode.HALF_EVEN).toString());
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

	public void doAuction(AuctionGoods auctionGoods, AuctionLogs auctionLogs, long userId, long preUserId)
			throws Exception {
		int a = auctionMapper.updateAuctionGoods(auctionGoods);
		if (a <= 0) {
			LOG.error("修改商品失败" + auctionGoods.getAuctionGoodsId() + "==" + userId + "==" + preUserId);
			throw new RuntimeException("系统异常交易失败");
		}
		int b = auctionLogsMapper.insertAuctionLogs(auctionLogs);
		if (b <= 0) {
			LOG.error("插入日志失败" + auctionGoods.getAuctionGoodsId() + "==" + userId + "==" + preUserId);
			throw new RuntimeException("系统异常交易失败");
		}
		// 资产操作
		String paramSign = userId + auctionLogs.getAuctionDiamond().toString();
		String sign = SignUtils.encryptHMAC(paramSign);
		Map<String, String> remoteMap = chezuAssetService.freezeDiamondAsset(userId,
				auctionLogs.getAuctionDiamond().doubleValue(), sign, preUserId, auctionGoods.getAuctionGoodsId());
		if (remoteMap == null) {
			throw new RuntimeException("系统异常交易失败");
		} else {
			if (remoteMap.get("flag").equals("0")) {
				throw new RuntimeException(remoteMap.get("msg"));
			}

		}
	}

	public void writeChangeInfo(Map<String, Object> data) {
		// 修改商品状态
		auctionMapper.updateGoodsStatus(Long.valueOf(data.get("auctionGoodsId").toString()),2);
		orderMapper.updateOrder(data);
	}
}
