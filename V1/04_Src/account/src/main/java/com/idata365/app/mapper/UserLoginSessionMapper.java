package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserLoginSession;

public interface UserLoginSessionMapper {

	
	void insertToken(UserLoginSession userLoginSession);
	
	UserLoginSession findToken(@Param("token") String token);
}
