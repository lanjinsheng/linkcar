package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.Order;
import com.idata365.app.entity.Prize;
import com.idata365.app.mapper.AuctionGoodMapper;
import com.idata365.app.mapper.OrderMapper;
import com.idata365.app.mapper.PrizeMapper;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ServerUtil;
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
	@Autowired
	private ChezuAppService chezuAppService;
	@Autowired
	private AuctionGoodMapper auctionGoodMapper;

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
			Prize prize = prizeMapper.selectByPrimaryKey(Long.valueOf(order.getPrizeId()));
			map.put("convertId", String.valueOf(order.getOrderId()));
			map.put("convertTime", DateTools.formatDateMD(order.getOrderTime()));
			map.put("rewardID", String.valueOf(order.getPrizeId()));
			map.put("rewardName", prize.getPrizeName());
			map.put("rewardDesc", prize.getPrizeDesc());
			map.put("rewardImg", prize.getPrizePic());
			map.put("convertType", order.getOrderType());
			map.put("convertStatus", order.getOrderStatus());
			map.put("convertNum", String.valueOf(order.getOrderNum()));

			list.add(map);
		}
		return list;
	}

	public String orderList(Map<String, Object> map) {
		List<Order> orders = orderMapper.orderList();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Order order : orders) {
			Map<String, String> rtMap = new HashMap<>();
			Prize prize = prizeMapper.selectByPrimaryKey(Long.valueOf(order.getPrizeId()));
			rtMap.put("convertId", String.valueOf(order.getOrderId()));
			rtMap.put("convertTime", DateTools.formatDateYMD(order.getOrderTime()));
			rtMap.put("userName", order.getUserName());
			rtMap.put("rewardID", String.valueOf(order.getPrizeId()));
			rtMap.put("rewardName", prize.getPrizeName());
			rtMap.put("rewardDesc", prize.getPrizeDesc());
			rtMap.put("rewardImg", prize.getPrizePic());
			rtMap.put("convertType", order.getOrderType());
			rtMap.put("convertStatus", order.getOrderStatus());
			rtMap.put("convertNum", String.valueOf(order.getOrderNum()));
			rtMap.put("diamondNum", order.getDiamondNum().toString());
			rtMap.put("phone", order.getPhone());
			rtMap.put("address", order.getAddress());

			list.add(rtMap);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(list));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	public String orderListVirtual(Map<String, Object> map) {
		List<Order> orders = orderMapper.orderListVirtual();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Order order : orders) {
			Map<String, String> rtMap = new HashMap<>();
			AuctionGoods goods = auctionGoodMapper.findAuctionGoodById(order.getPrizeId());
			rtMap.put("convertId", String.valueOf(order.getOrderId()));
			rtMap.put("convertTime", DateTools.formatDateYMD(order.getOrderTime()));
			rtMap.put("userName", order.getUserName());
			rtMap.put("rewardID", String.valueOf(order.getPrizeId()));
			rtMap.put("rewardName", goods.getPrizeName());
			rtMap.put("rewardDesc", goods.getPrizeDesc());
			rtMap.put("rewardImg", goods.getPrizePic());
			rtMap.put("convertType", order.getOrderType());
			rtMap.put("convertStatus", order.getOrderStatus());
			rtMap.put("convertNum", String.valueOf(order.getOrderNum()));
			rtMap.put("diamondNum", order.getDiamondNum().toString());
			rtMap.put("phone", order.getPhone());
			rtMap.put("address", order.getAddress());

			list.add(rtMap);
		}
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(list));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}

	/**
	 * 
	 * @param ofUserId
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
	public void save(Order order, long ofUserId) throws Exception {
		int ordernum = order.getOrderNum();
		long prizeId = order.getPrizeId();

		int f = prizeMapper.div(ordernum, prizeId);
		if (f <= 0) {
			throw new RuntimeException("库存不足");
		}

		orderMapper.insert(order);

		String paramSign = order.getUserId() + String.valueOf(order.getDiamondNum());
		String sign = SignUtils.encryptDataAes(paramSign);
		boolean flag = chezuAssetService.submitDiamondAsset(order.getUserId(), order.getDiamondNum().doubleValue(),
				sign, ofUserId);
		if (!flag) {
			throw new RuntimeException("交易失败");
		}

	}

	public int sendReward(Long orderId, String operatingUser) {
		return orderMapper.sendReward(orderId, operatingUser);
	}

	public int sendVirtualReward(Long orderId, String operatingUser) {
		Order order = orderMapper.getOrderByOrderId(orderId);
		Long goodsId = order.getPrizeId();
		AuctionGoods goods = auctionGoodMapper.findAuctionGoodById(goodsId);
		int a = auctionGoodMapper.updateGoodsStatus(goodsId,4);//4.交易成功
		int b = orderMapper.sendReward(orderId, operatingUser);
		chezuAppService.sendAuctionMsg(goods.getAuctionGoodsId(), goods.getAuctionGoodsType(), 2, String.valueOf(order.getUserId()), goods.getPrizeName(), SignUtils.encryptHMAC(String.valueOf(order.getUserId())));
		return a + b;
	}

}
