package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicUserMission;

public interface DicUserMissionMapper {

	public List<DicUserMission> getAllDicUserMission();

	public List<Integer> getParentMissionId(@Param("missionType") Integer missionType);

}
