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
			if (order.getPrizeid() == PRIZE_NO10 || order.getPrizeid() == PRIZE_NO25
					|| order.getPrizeid() == PRIZE_NO50) {
				flag = true;
			}
		}

		List<Prize> prizes = prizeMapper.selectByExample();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Prize prize : prizes) {
			long prizeId = prize.getPrizeid();
			if (flag && (prizeId == PRIZE_NO10 || prizeId == PRIZE_NO25 || prizeId == PRIZE_NO50)) {
				continue;
			}
			Map<String, String> map = new HashMap<>();
			map.put("rewardID", String.valueOf(prize.getPrizeid()));
			map.put("rewardName", prize.getPrizename());
			map.put("rewardDesc", prize.getPrizedesc()+"  库存："+prize.getStock()+"件");
			map.put("rewardImg", prize.getPrizepic());
			map.put("originalPrice", String.valueOf(prize.getOriginalprice()));
			map.put("diamondValue", String.valueOf(prize.getDiamondvalue()));
			map.put("rewardDetailPics", prize.getPrizedetailpics());
			map.put("rewardDetailTexts", prize.getPrizedetailtexts()+"  库存："+prize.getStock()+"件");
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
			map.put("rewardID", String.valueOf(prize.getPrizeid()));
			map.put("rewardName", prize.getPrizename());
			map.put("rewardDesc", prize.getPrizedesc()+"  库存："+prize.getStock()+"件");
			map.put("rewardImg", prize.getPrizepic());
			map.put("originalPrice", String.valueOf(prize.getOriginalprice()));
			map.put("diamondValue", String.valueOf(prize.getDiamondvalue()));
			map.put("rewardDetailPics", prize.getPrizedetailpics());
			map.put("rewardDetailTexts", prize.getPrizedetailtexts()+"  库存："+prize.getStock()+"件");
			map.put("isMarketable", String.valueOf(prize.getIsMarketable()));
			map.put("stock", prize.getStock().toString());

			list.add(map);
		}
		return list;
	}
	

	public Prize getPrize(Long prizeid) {
		Prize prize = prizeMapper.selectByPrimaryKey(prizeid);
		return prize;
	}

	public void save(Prize prize) {
		prizeMapper.insert(prize);
	}

	public void update(Prize prize) {
		prizeMapper.updateByPrimaryKey(prize);
	}

	public void delete(Long id) {
		prizeMapper.deleteByPrimaryKey(id);
	}

}
