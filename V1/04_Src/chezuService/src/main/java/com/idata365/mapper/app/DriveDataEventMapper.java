package com.idata365.mapper.app;

import java.util.Map;

/**
 * 
    * @ClassName: DriveDataLogMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface DriveDataEventMapper {
    void insertDriveEvent(Map<String,Object> alarmMap);
}
