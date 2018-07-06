package com.idata365.app.mapper;



import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.InteractPeccancy;


public interface InteractPeccancyMapper {
	int insertPeccancy(InteractPeccancy interactPeccancy);
	
	int updatePeccancy(InteractPeccancy interactPeccancy);
	
	InteractPeccancy getPeccancy(@Param("id") Long id);
}
