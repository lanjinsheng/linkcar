package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.v2.UserLookAdLogs;

public interface UserLookAdMapper {

	public int getTodayCount(@Param("userId") Long userId, @Param("daystamp") String daystamp);
	
	public int insertLogs(UserLookAdLogs logs);
	
	public UserLookAdLogs getUserLastLookInfo(@Param("userId") Long userId);
}
