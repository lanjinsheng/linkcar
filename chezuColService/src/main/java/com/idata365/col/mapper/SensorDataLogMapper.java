package com.idata365.col.mapper;

import java.util.List;

import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.SensorDataLog;
/**
 * 
    * @ClassName: DriveDataLogMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface SensorDataLogMapper {

    void insertSensorLog(SensorDataLog log);
    void delSensorLog(SensorDataLog log);
    List<SensorDataLog> listSensorLogByUH(DriveDataLog log);
}
