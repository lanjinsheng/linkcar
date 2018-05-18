package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicGameDay;

public interface DicGameDayMapper
{
	public  List<DicGameDay>  getDicGameDay(Map<String,Object> searchMap);
    DicGameDay  getGameDicByDaystamp(@Param("daystamp") String daystamp);
	 
}
