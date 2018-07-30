package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.UserCar;

public interface UserCarMapper {

	public int insertUserCar(UserCar userCar);
	
	public List<UserCar> getUserCarById(@Param("userId") Long userId);
	
	public UserCar getCarInfo(@Param("userCarId") Long userCarId);
	
	public UserCar getUserCurCar(@Param("userId") Long userId);
	
	public void updateInUse(@Param("inUse") int inUse,@Param("userCarId") Long userCarId);
}
