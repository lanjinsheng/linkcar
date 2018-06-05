package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.LicenseDriver;

public interface LicenseDriverMapper {

	void insertImgDriverFrontImg(Map<String, Object> jsz);

	void modifyImgDriverFront(Map<String, Object> jsz);

	void insertImgDriverBackImg(Map<String, Object> jsz);

	LicenseDriver findLicenseDriverByUserId(@Param("userId") Long userId);

	List<LicenseDriver> getUserLicenseDrivers();
	
	int verifyLicenseDriver(@Param("userId")Long userId, @Param("operatingUser")String operatingUser);
	
}
