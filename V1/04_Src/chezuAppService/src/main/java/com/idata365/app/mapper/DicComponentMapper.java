package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.v2.DicComponent;

public interface DicComponentMapper {
	public List<DicComponent> getDicComponent(Map<String, Object> searchMap);

	public Double getCountCurComponentByUserIdCarId(@Param("userId") Long userId, @Param("userCarId") Long userCarId);

	public List<DicComponent> getCurComponentByUserIdCarId(@Param("userId") Long userId, @Param("userCarId") Long userCarId);

}
