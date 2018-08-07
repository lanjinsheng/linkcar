package com.idata365.mapper.app;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface UsersAccountMapper {
	Long getUserIdByToken(@Param("token") String token);
}
