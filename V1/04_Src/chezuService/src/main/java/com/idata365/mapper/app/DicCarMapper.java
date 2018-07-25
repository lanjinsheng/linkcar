package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.DicCar;


public interface DicCarMapper {
	public List<DicCar> getDicCar(Map<String, Object> searchMap);

	public DicCar getCarByCarId(@Param("carId") Integer carId);
}
