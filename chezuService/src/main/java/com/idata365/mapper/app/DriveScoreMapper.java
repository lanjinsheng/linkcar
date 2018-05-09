package com.idata365.mapper.app;

import java.util.List;

import com.idata365.entity.DriveScore;

public interface DriveScoreMapper {

	void insertScore(DriveScore driveScore);
	List<DriveScore> getDriveScoreByUH(DriveScore driveScore);
	List<DriveScore> getDriveScoreByUHF(DriveScore driveScore);
	void insertScoreList(List<DriveScore> list);
	
	List<DriveScore> getDriveScoreByUR(DriveScore driveScore);
}
