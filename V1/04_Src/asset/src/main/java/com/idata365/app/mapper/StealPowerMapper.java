package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import com.idata365.app.entity.StealPower;

public interface StealPowerMapper {

	int insertSteal(StealPower stealPower);
    List<StealPower> getStealPowerList(Map<String,Object> map);
 
}
