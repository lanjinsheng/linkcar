package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.TempPowerReward;

public interface TempPowerRewardMapper {

	int batchInsertTempPowerReward(List<TempPowerReward> tempPowerReward);

	TempPowerReward getTempPowerReward(@Param("uuid") String uuid);

	int updateTempPowerReward(@Param("uuid") String uuid);
}
