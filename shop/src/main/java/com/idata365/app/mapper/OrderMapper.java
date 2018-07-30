package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Order;

public interface OrderMapper {

	int insert(Order record);

	List<Order> selectByExample(@Param("userId")Long userId);
	
	Order getOrderByOrderId(@Param("orderId")Long orderId);
	
	Order getOrderByGoodsIdUserId(@Param("prizeId")Long prizeId,@Param("userId")Long userId);
	
	List<Order> orderList();
	
	List<Order> orderListVirtual();

	int sendReward(@Param("convertId") Long convertId, @Param("operatingUser") String operatingUser);

	int updateOrder(Map<String,Object> data);
	
	int updateOrderStatus(@Param("orderId") long orderId, @Param("orderStatus") String orderStatus);
}