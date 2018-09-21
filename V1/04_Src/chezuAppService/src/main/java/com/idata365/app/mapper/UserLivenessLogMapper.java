package com.idata365.app.mapper;

import com.idata365.app.entity.v2.DicLiveness;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserLivenessLogMapper {

    List<DicLiveness> getLiveness();

    int insertUserLivenessLog(Map<String, Object> map);

    List<Map<String, Object>> getUserLivenessLog(@Param("userId") long userId);

    int getTodayCountById(@Param("userId") long userId, @Param("livenessId") int livenessId);

    int get1DayLivenessValue(@Param("userId") long userId);

    int get7DayLivenessValue(@Param("userId") long userId);

    int getYesterdayLivenessValueByFamilyId(@Param("familyId") long familyId);
}
