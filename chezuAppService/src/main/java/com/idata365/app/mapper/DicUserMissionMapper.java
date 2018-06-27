package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.DicFamilyType;
import com.idata365.app.entity.DicUserMission;

public interface DicUserMissionMapper {
	
	public List<DicUserMission> getAllDicUserMission();

}
