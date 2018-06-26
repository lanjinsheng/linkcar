package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.StealPower;

public interface StealPowerMapper {

	int insertSteal(StealPower stealPower);

	List<StealPower> getStealPowerList(@Param("familyId") long familyId,@Param("fightFamilyId")long fightFamilyId);
	
	List<StealPower> getStealPowerListByUserId(@Param("userId") long userId);

	long getReceiveNum(@Param("familyId") long familyId);
}
