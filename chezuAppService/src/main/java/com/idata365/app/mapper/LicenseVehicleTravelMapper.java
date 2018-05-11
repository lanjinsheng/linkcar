package com.idata365.app.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.LicenseDriver;
import com.idata365.app.entity.LicenseVehicleTravel;

public interface LicenseVehicleTravelMapper {

	 void  insertImgVehicleFrontImg(Map<String,Object> xsz);
	 void  modifyImgVehicleFront(Map<String,Object> xsz);
	 void  insertImgVehicleBackImg(Map<String,Object> xsz);
	 LicenseVehicleTravel  findLicenseVehicleTravelByUserId(@Param("userId")  Long userId);
}