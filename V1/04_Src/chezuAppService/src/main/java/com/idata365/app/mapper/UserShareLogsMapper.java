package com.idata365.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserShareLogsMapper {

	public int insertShareLogs(Map<String,Object> map);
	
	public int queryUserShareCountToday(@Param("userId")long userId);
	
}
