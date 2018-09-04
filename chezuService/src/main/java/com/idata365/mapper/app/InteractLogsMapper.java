package com.idata365.mapper.app;

import org.apache.ibatis.annotations.Param;

public interface InteractLogsMapper {

    int validCleanCarPowerUp(@Param("userCarId") long userCarId);

}
