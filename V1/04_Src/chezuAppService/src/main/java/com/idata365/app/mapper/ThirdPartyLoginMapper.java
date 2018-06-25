package com.idata365.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ThirdPartyLoginMapper {
	
	public Map<String, Object> queryThirdPartyLoginById(@Param("openId") String openId);
	
	public Map<String, Object> queryThirdPartyLoginByUserId(@Param("userId") Long userId);

	public int insertLogs(Map<String, Object> entity);

	public int updateByOpenId(@Param("userId")Long userId, @Param("openId")String openId);

}
