package com.idata365.mapper.app;

import org.apache.ibatis.annotations.Param;


public interface UserCarMapper {

	public int sendUserCarAndCarIdIs2(@Param("userId") Long userId);

}
