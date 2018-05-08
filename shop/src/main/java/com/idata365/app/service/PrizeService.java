package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.Prize;
import com.idata365.app.mapper.PrizeMapper;

@Service
@Transactional
public class PrizeService {

	@Autowired
	private PrizeMapper prizeMapper;

	public List<Map<String, String>> getPrizes() {
		List<Prize> prizes = prizeMapper.selectByExample(null);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Prize prize : prizes) {
			Map<String, String> map = new HashMap<>();
			map.put("rewardID", String.valueOf(prize.getPrizeid()));
			map.put("rewardName", prize.getPrizename());
			map.put("rewardDesc", prize.getPrizedesc());
			map.put("rewardImg", prize.getPrizepic());
			map.put("originalPrice", String.valueOf(prize.getOriginalprice()));
			map.put("diamondValue", String.valueOf(prize.getDiamondvalue()));
			map.put("rewardDetailPics", prize.getPrizedetailpics());
			map.put("rewardDetailTexts", prize.getPrizedetailtexts());
			map.put("isMarketable", String.valueOf(prize.getIsMarketable()));

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
