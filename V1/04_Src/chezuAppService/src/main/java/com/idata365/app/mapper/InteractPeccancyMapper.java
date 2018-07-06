package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.InteractPeccancy;

public interface InteractPeccancyMapper {
	int insertPeccancy(InteractPeccancy interactPeccancy);

	int updatePeccancy(InteractPeccancy interactPeccancy);

	InteractPeccancy getPeccancy(@Param("id") Long id);

	int isCanPayTicket(@Param("userId") long userId);
	
	List<Map<String,Object>> getPeccancyList(Map<String,Object> map);
	

}
