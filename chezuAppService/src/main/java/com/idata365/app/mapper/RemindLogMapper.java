package com.idata365.app.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RemindLogMapper {


    public Integer queryTodayRemindCount(@Param("userId") Long userId, @Param("familyId") Long familyId);

    public Integer insertRemindLog(Map<String, Object> log);

    public List<Long> queryTodayRemindUserIdsByFamilyId(@Param("familyId") Long familyId);
}
