package com.idata365.col.mapper;

import java.util.List;
import java.util.Map;

import com.idata365.col.entity.DriveDataEvent;
import com.idata365.col.entity.DriveDataMain;

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
    
    List<DriveDataEvent> listDriveEventByMainId(DriveDataMain drive);
   
}
