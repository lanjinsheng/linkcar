package com.idata365.mapper.app;


import org.apache.ibatis.annotations.Param;

import com.idata365.entity.DicGameDay;

public interface DicGameDayMapper
{

	
	public DicGameDay queryDicGameDay(@Param("day") String day);
 
	public int insertDicGameDay(DicGameDay dicGameDay);
}
