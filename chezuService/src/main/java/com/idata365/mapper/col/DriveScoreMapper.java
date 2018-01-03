package com.idata365.mapper.col;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.DriveScore;

public interface DriveScoreMapper {

	void insertScore(DriveScore driveScore);
	List<DriveScore> getDriveScoreByUH(DriveScore driveScore);
	void insertScoreList(List<DriveScore> list);
}
