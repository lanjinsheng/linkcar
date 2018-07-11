package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Carpool;


public interface CarpoolMapper {
 
	List<Map<String,Object>> getCarPool(@Param("userCarLogsId") Long userCarLogsId);
	
	int insertCarpool(Carpool carpool);
	
	int offCar(Carpool carpool);
	
	int getPassengersNum(@Param("userCarLogsId") Long userCarLogsId);
	int isDriver(@Param("driverId") Long driverId);
	
	int waitHave(@Param("userId") Long userId);
	
	int querySitsNum(@Param("userId") Long userId);
}
