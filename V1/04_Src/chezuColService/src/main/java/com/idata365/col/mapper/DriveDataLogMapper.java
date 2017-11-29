package com.idata365.col.mapper;

import java.util.List;
import java.util.Map;

import com.idata365.col.entity.DriveDataLog;
/**
 * 
    * @ClassName: DriveDataLogMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface DriveDataLogMapper {

    void insertDataLog(DriveDataLog log);
    void delDataLog(DriveDataLog log);
    DriveDataLog getDriveDataLog(Long id);
    List<DriveDataLog> listPageDriveLog(Map<String,Object> map);
    List<DriveDataLog> listDriveLogByUH(DriveDataLog log);
    
    
    
    List<DriveDataLog> getUploadDataStatus(Map<String,Object> map);
}
