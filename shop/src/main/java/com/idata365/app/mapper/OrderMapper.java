package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Order;

public interface OrderMapper {

    int insert(Order record);
    
    List<Order> selectByExample(Long userId);
    
    List<Order> orderList();

	int sendReward(@Param("convertId")Long convertId, @Param("operatingUser")String operatingUser);
}