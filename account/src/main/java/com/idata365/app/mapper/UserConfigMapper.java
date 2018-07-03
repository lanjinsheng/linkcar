package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserConfig;

public interface UserConfigMapper {

	Integer queryIsGPSHidden(@Param("userId") Long userId);

	Integer queryIsCanInvite(@Param("userId") Long userId);

	Integer queryIsCanJoinMe(@Param("userId") Long userId);

	void updateUserConfig(UserConfig userConfig);

}
