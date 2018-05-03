package com.idata365.app.mapper;

 
import java.util.Map;

import org.apache.ibatis.annotations.Param;

 

public interface TripMapper
{
 
	public Map<String,Object> getTripByUserLatest(@Param("userId") Long userId);
	public Map<String,Object> getTripSpeciality(@Param("travelId") Long travelId);
	
	 
}
