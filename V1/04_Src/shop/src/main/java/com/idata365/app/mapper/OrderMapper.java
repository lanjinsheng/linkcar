package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.Order;

public interface OrderMapper {

    int insert(Order record);

    List<Order> selectByExample(Long userId);

    int updateByPrimaryKey(Order record);
}