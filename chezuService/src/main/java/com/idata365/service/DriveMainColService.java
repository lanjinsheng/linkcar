package com.idata365.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.DriveDataMain;
import com.idata365.mapper.col.DriveDataMainMapper;

@Service
public class DriveMainColService extends BaseService<DriveMainColService>{
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Transactional(value="colTransactionManager")
	public DriveDataMain getDriveMain(Long userId,Long habitId) {
		//获取行程main，获取行程event
		DriveDataMain main=new DriveDataMain();
		main.setUserId(userId);
		main.setHabitId(habitId);
		DriveDataMain dm=driveDataMainMapper.getDriveDataMainByUH(main);
		return dm;
	}
}
