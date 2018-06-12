package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TripMapper {

	public Map<String, Object> getTripByUserLatest(@Param("userId") Long userId);

	public Map<String, Object> getTripSpeciality(@Param("travelId") Long travelId);

	public List<Map<String, Object>> getTripLatest(Map<String, Object> map);

	public String getTripEndTimeOfUser(@Param("userId") Long userId);
	
	public void getHiddenTrip(@Param("travelId") Long travelId);
}
