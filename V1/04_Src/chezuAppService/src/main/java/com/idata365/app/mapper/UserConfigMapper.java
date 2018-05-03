package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserConfig;
import com.idata365.app.entity.UsersAccount;

public interface UserConfigMapper {

	UserConfig  getUserConfigById(@Param("userId") Long userId);
	  
	 void  updateUserConfig(UserConfig userConfig);
	 
}
