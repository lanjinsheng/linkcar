package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.InteractTempCar;

public interface InteractTempCarMapper {

	int lockCar(InteractTempCar interactTempCar);
	int lockCarById(InteractTempCar interactTempCar);
	List<InteractTempCar> getTempCar(@Param("uuid") String uuid);
	List<InteractTempCar> getTempCarByUserId(InteractTempCar interactTempCar);

	
	void clearLockTask(@Param("compareTimes") Long compareTimes);
	
	InteractTempCar getTempCarByUuid(@Param("uuid") String uuid);

	int batchInsertCar(List<Map<String, Object>> list);

	int updateTempCar(Map<String, Object> map);

	int updateBlackIds(Map<String, Object> map);

	int isCanStealPower(@Param("userId") long userId);
}
