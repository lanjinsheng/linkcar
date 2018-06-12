package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.Order;
import com.idata365.app.entity.Prize;
import com.idata365.app.mapper.OrderMapper;
import com.idata365.app.mapper.PrizeMapper;

@Service
@Transactional
public class PrizeService {
	public static final long PRIZE_NO10 = 149187842867964L;
	public static final long PRIZE_NO25 = 149187842867965L;
	public static final long PRIZE_NO50 = 149187842867966L;
	@Autowired
	private PrizeMapper prizeMapper;
	@Autowired
	private OrderMapper orderMapper;

	public List<Map<String, String>> getPrizes(long userId) {
		boolean flag = false;
		List<Order> orders = orderMapper.selectByExample(userId);
		for (Order order : orders) {
			if (order.getPrizeId() == PRIZE_NO10 || order.getPrizeId() == PRIZE_NO25
					|| order.getPrizeId() == PRIZE_NO50) {
				flag = true;
			}
		}

		List<Prize> prizes = prizeMapper.selectByExample();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Prize prize : prizes) {
			long prizeId = prize.getPrizeId();
			if (flag && (prizeId == PRIZE_NO10 || prizeId == PRIZE_NO25 || prizeId == PRIZE_NO50)) {
				continue;
			}
			Map<String, String> map = new HashMap<>();
			map.put("rewardID", String.valueOf(prize.getPrizeId()));
			map.put("rewardName", prize.getPrizeName());
			map.put("rewardDesc", prize.getPrizeDesc()+"  库存："+prize.getStock()+"件");
			map.put("rewardImg", prize.getPrizePic());
			map.put("originalPrice", String.valueOf(prize.getOriginalPrice()));
			map.put("diamondValue", String.valueOf(prize.getDiamondValue()));
			map.put("rewardDetailPics", prize.getPrizeDetailPics());
			map.put("rewardDetailTexts", prize.getPrizeDetailTexts()+"  库存："+prize.getStock()+"件");
			map.put("isMarketable", String.valueOf(prize.getIsMarketable()));
			map.put("stock", prize.getStock().toString());

			list.add(map);
		}
		return list;
	}
	
	
	public List<Map<String, String>> getPrize() {

		List<Prize> prizes = prizeMapper.selectByExample();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Prize prize : prizes) {
			Map<String, String> map = new HashMap<>();
			map.put("rewardID", String.valueOf(prize.getPrizeId()));
			map.put("rewardName", prize.getPrizeName());
			map.put("rewardDesc", prize.getPrizeDesc()+"  库存："+prize.getStock()+"件");
			map.put("rewardImg", prize.getPrizePic());
			map.put("originalPrice", String.valueOf(prize.getOriginalPrice()));
			map.put("diamondValue", String.valueOf(prize.getDiamondValue()));
			map.put("rewardDetailPics", prize.getPrizeDetailPics());
			map.put("rewardDetailTexts", prize.getPrizeDetailTexts()+"  库存："+prize.getStock()+"件");
			map.put("isMarketable", String.valueOf(prize.getIsMarketable()));
			map.put("stock", prize.getStock().toString());

			list.add(map);
		}
		return list;
	}
	

	public Prize getPrize(Long prizeId) {
		Prize prize = prizeMapper.selectByPrimaryKey(prizeId);
		return prize;
	}

}
