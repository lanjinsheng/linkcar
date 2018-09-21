package com.idata365.app.mapper;

import com.idata365.app.entity.UserScoreDayStat;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserScoreDayStatMapper {

    List<UserScoreDayStat> getUsersDayScoreByFamily(UserScoreDayStat userScoreDayStat);

    UserScoreDayStat getTodayAllTravel(@Param("userId") Long userId, @Param("daystamp") String daystamp);
}
