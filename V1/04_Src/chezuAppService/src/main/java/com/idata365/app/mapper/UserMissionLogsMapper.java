package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicUserMission;
import com.idata365.app.entity.UserMissionLogs;

public interface UserMissionLogsMapper {

	public List<UserMissionLogs> getLogsByUserId(@Param("userId") long userId);

	public void initLogsToUser(@Param("missionsList") List<DicUserMission> missions, @Param("userId") long userId);

	public List<Map<String, Object>> getLogsByUserIdAndType(@Param("userId") long userId,
			@Param("missionType") int missionType);

	public int updateLogsStatus(@Param("userId") long userId, @Param("missionId") int missionId);

}
