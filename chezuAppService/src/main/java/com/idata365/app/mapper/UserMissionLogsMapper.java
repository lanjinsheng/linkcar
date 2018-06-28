package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicUserMission;
import com.idata365.app.entity.UserMissionLogs;

public interface UserMissionLogsMapper {

	public List<UserMissionLogs> getLogsByUserId(@Param("userId") long userId);

	public int initLogsToUser(@Param("missionsList") List<DicUserMission> missions, @Param("userId") long userId);

	public List<Map<String, Object>> getLogsByUserIdAndType(@Param("userId") long userId,
			@Param("missionType") int missionType);

	public int updateLogsStatus(@Param("userId") long userId, @Param("missionId") int missionId,
			@Param("status") int status);

	public int updateLogs(@Param("userId") long userId, @Param("missionId") int missionId, @Param("status") int status,
			@Param("finishCount") int finishCount);

	public int updateLogsFinishCount(@Param("userId") long userId, @Param("missionId") int missionId);

	public int updateLogsValid(@Param("userId") long userId, @Param("missionId") int missionId);

	public int insertOneLogs(UserMissionLogs userMissionLogs);

	public Map<String, Object> getOneLogsByUserIdAndMissionId(@Param("userId") long userId,
			@Param("missionId") int missionId);

	public int queryCountByType(@Param("userId") long userId,@Param("missionType") int missionType);

	public int updateCountOfId5(@Param("userId") long userId);

	public int queryHadRecord(@Param("userId") long userId);
	
	public UserMissionLogs queryMissionId1(@Param("userId") long userId);

	public int updateValid(@Param("userId") long userId);

}
