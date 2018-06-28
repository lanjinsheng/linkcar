package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserShareLogsMapper {

	public int insertShareLogs(@Param("userId")long userId);
	
	public int queryUserShareCountToday(@Param("userId")long userId);
	
}
