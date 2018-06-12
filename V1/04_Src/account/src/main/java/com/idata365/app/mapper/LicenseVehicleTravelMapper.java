package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;

public interface LicenseVehicleTravelMapper {

	void insertImgVehicleFrontImg(Map<String, Object> xsz);

	List<LicenseVehicleTravel> findLicenseVehicleTravelByUserId(@Param("userId") Long userId);

	List<LicenseVehicleTravel> findLicenseVehicleTravels();

	int verifyLicenseVehicleTravel(Map<String, Object> xsz);
}
