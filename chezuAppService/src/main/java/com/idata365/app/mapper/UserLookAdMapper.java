package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.v2.UserLookAdLogs;

public interface UserLookAdMapper {
	
	public int getTodayCount(@Param("userId") Long userId, @Param("daystamp") String daystamp);
	
	public int getTodayCountAllType(@Param("userId") Long userId, @Param("daystamp") String daystamp);
	
	public int insertLogs(UserLookAdLogs logs);
	
	public UserLookAdLogs getUserLastLookInfo(@Param("userId") Long userId);
	
	public Long getAllPowerNumOfType2(@Param("userId") Long userId);
	
	public int countOfLoadFlagZero(@Param("userId") Long userId);
	
	public List<String> queryTodayLoadFlag(@Param("userId") Long userId);
	
	int updateHadGet(@Param("userId") Long userId,@Param("adPassId") Long adPassId, @Param("daystamp") String daystamp);
}
