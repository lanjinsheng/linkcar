package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.InteractTempCar;



public interface InteractTempCarMapper {

	int batchInsertTempCar(List<InteractTempCar> tempPowerReward);
}
