package com.idata365.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.AssetFamiliesPowerLogs;

public interface AssetFamiliesPowerLogsMapper {

	int insertFamiliesPowerLogs(AssetFamiliesPowerLogs assetFamiliesPowerLogs);
	
	int updateFamiliesPowerLogs(@Param("ballId") long ballId, @Param("powerNum") long powerNum, @Param("count") long count);
	
	List<AssetFamiliesPowerLogs> getFamilyPowers(@Param("familyId") long familyId,
			@Param("fightFamilyId") long fightFamilyId);
	
	AssetFamiliesPowerLogs getFamiliesPowerLogs(@Param("ballId") long ballId);
	
	long getContributionNum(@Param("familyId") long familyId);
}
