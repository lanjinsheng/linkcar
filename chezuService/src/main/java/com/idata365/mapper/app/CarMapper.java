package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



public interface CarMapper {
 
	Map<String,Object> getUserCar(Map<String,Object> map);
	
	List<Map<String,Object>> getCarComponents(@Param("userCarId") Long userCarId);
	
	int insertComponentUserUseLog(Map<String,Object> map);
	
	int updateCarComponents(@Param("id") Long id);
	
}
