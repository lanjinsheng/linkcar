package com.idata365.app.mapper;


import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicGameDay;

public interface DicGameDayMapper
{
//	public  List<DicGameDay>  getDicGameDay(Map<String,Object> searchMap);
    DicGameDay  getGameDicByDaystamp(@Param("daystamp") String daystamp);
	 
}
