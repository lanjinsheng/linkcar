package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.Carpool;


public interface CarpoolMapper {
 
	List<Map<String,Object>> getCarPool(@Param("userCarLogsId") Long userCarLogsId);
	List<Map<String,Object>> getCarpoolRecords(Map<String,Object> map);

	Map<String,Object> getSelfTravelBy(@Param("passengerId") Long passengerId);
	int insertCarpool(Carpool carpool);
	
	int offCar(Carpool carpool);
	
	int getPassengersNum(@Param("userCarLogsId") Long userCarLogsId);
	int isDriver(@Param("driverId") Long driverId);
	
	int waitHave(@Param("userId") Long userId);
	
	int querySitsNum(@Param("userId") Long userId);
	
	int querySitsNumById(@Param("userId") Long userId,@Param("carId") Integer carId);
	
	Integer getTodayLiftCountByUserId(@Param("passengerId") Long passengerId);
	
	Integer getTotalLiftCountByUserId(@Param("passengerId") Long passengerId);
}
