package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface CarpoolMapper {
 
	List<Map<String,Object>> getCarPool(@Param("driverId") Long driverId);
	int updateCarPool(Map<String,Object> map);
	int clearCarPool(@Param("daystamp") String daystamp);
	
}
