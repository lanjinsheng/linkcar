package com.idata365.mapper.app;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.UserConfig;

public interface UserConfigMapper {

	UserConfig  getUserConfigById(@Param("userId") Long userId);
	List<UserConfig>  getUserConfig();
	 
}
