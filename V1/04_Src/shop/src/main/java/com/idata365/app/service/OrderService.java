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
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.SignUtils;

/**
 * @ClassName: OrderService
 * @Description: TODO(关于兑换请求的相关操作)
 * @author LiXing
 * @date 2018年5月2日
 *
 */

@Service

public class OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private PrizeMapper prizeMapper;
	@Autowired
	private ChezuAssetService chezuAssetService;

	/**
	 * 
	 * @Title: getOrders
	 * @Description: TODO(获取所有交易详情)
	 * @param @return
	 *            参数
	 * @return List<Order> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Order> getOrders() {
		List<Order> orders = orderMapper.selectByExample(null);
		return orders;
	}

	/**
	 * 
	 * @Title: getOrderList
	 * @Description: TODO(获取当前用户所有交易详情)
	 * @param @param
	 *            userId
	 * @param @return
	 *            参数
	 * @return List<Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	public List<Map<String, String>> getOrderList(Long userId) {
		List<Order> orders = orderMapper.selectByExample(userId);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Order order : orders) {
			Map<String, String> map = new HashMap<>();
			Prize prize = prizeMapper.selectByPrimaryKey(Long.valueOf(order.getPrizeid()));
			map.put("convertId", String.valueOf(order.getOrderId()));
			map.put("convertTime", DateTools.formatDateMD(order.getOrdertime()));
			map.put("rewardID", String.valueOf(order.getPrizeid()));
			map.put("rewardName", prize.getPrizename());
			map.put("rewardDesc", prize.getPrizedesc());
			map.put("rewardImg", prize.getPrizepic());
			map.put("convertType", order.getOrdertype());
			map.put("convertStatus", order.getOrderstatus());
			map.put("convertNum", String.valueOf(order.getOrdernum()));

			list.add(map);
		}
		return list;
	}

	/**
	 * 
	 * @Title: save
	 * @Description: TODO(保存交易记录操作)
	 * @param @param
	 *            order
	 * @param @throws
	 *            Exception 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@Transactional
	public void save(Order order) throws Exception {
		int ordernum = order.getOrdernum();
		long prizeId = order.getPrizeid();
		
		int f = prizeMapper.div(ordernum,prizeId);
		if(f<=0) {
			throw new RuntimeException("库存不足");
		}
		
		orderMapper.insert(order);
		
		String paramSign = order.getUserid() + String.valueOf(order.getDiamondnum());
		String sign = SignUtils.encryptDataAes(paramSign);
		boolean flag = chezuAssetService.submitDiamondAsset(order.getUserid(), order.getDiamondnum(), sign);
		if (!flag) {
			throw new RuntimeException("交易失败");
		}

	}

	public void update(Order order) {
		orderMapper.updateByPrimaryKey(order);
	}

}
