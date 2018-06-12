package com.idata365.app.mapper;

 

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserConfig;

public interface UserConfigMapper {

	UserConfig  getUserConfigById(@Param("userId") Long userId);
	  
	 void  updateUserConfig(UserConfig userConfig);
	 
}
