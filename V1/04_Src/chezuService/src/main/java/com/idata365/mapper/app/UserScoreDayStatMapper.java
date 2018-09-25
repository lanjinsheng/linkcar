package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserScoreDayStat;

public interface UserScoreDayStatMapper {
    //	int updateUserScoreDayStat(UserScoreDayStat userScoreDayStat);//更新驾驶行为
    int updateUserScoreDayById(UserScoreDayStat userScoreDayStat);//更新驾驶得分

    void initUserDayScore(Map<String, Object> map);

    void initUserFamilyRoleLog(Map<String, Object> map);

    List<UserScoreDayStat> getUsersDayScoreByFamily(UserScoreDayStat userScoreDayStat);

    List<UserScoreDayStat> getUsersDayScoreByUserId(@Param("userId") Long userId, @Param("daystamp") String daystamp);

    int insertOrUpdateUserDayStat(UserScoreDayStat userScoreDayStat);

    UserScoreDayStat getUserDayScoreByUserFamily(UserFamilyRoleLog userFamilyRoleLog);

    int updateUserDayStat(UserScoreDayStat userScoreDayStat);

    void lockUserDayScoreTask(UserScoreDayStat userScoreDayStat);

    List<UserScoreDayStat> getUserDayScoreTask(UserScoreDayStat userScoreDayStat);

    void updateUserDayScoreSuccTask(UserScoreDayStat userScoreDayStat);

    void updateUserDayScoreFailTask(UserScoreDayStat userScoreDayStat);

    void clearLockTask(@Param("compareTimes") Long compareTimes);

    int getHadScoreMembersToBox(@Param("daystamp") String daystamp, @Param("familyId") Long familyId);

}
