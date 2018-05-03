package com.idata365.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DriveScore;
import com.idata365.mapper.col.DriveScoreMapper;
@Service
public class DriveScoreService extends BaseService<DriveScoreService>{
	@Autowired
	DriveScoreMapper driveScoreMapper;
	@Transactional(value="colTransactionManager")
	List<DriveScore> getDriveScoreByUHF(DriveScore driveScore){
		return driveScoreMapper.getDriveScoreByUHF(driveScore);
	}
}
