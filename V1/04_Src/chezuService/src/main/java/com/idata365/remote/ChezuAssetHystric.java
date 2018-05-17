package com.idata365.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.entity.bean.AssetFamiliesPowerLogs;
import com.idata365.entity.bean.AssetUsersPowerLogs;
import com.idata365.entity.bean.FamilyGameAsset;
import com.idata365.entity.bean.FamilySeasonAsset;


@Component
public class ChezuAssetHystric implements ChezuAssetService{
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAssetHystric.class);

	@Override
	public boolean addPowerUsersTask(String jsonValue, String sign,AssetUsersPowerLogs assetUsersPowerLogs) {
		// TODO Auto-generated method stub
		LOG.error("addPowerUsersTask挂了:"+jsonValue);
		return false;
	}

	@Override
	public boolean addPowerFamilyTask(String jsonValue, String sign,AssetFamiliesPowerLogs assetFamiliesPowerLogs) {
		// TODO Auto-generated method stub
		LOG.error("addPowerFamilyTask挂了:"+jsonValue);
		return false;
	}

	@Override
	public boolean addFamilyGameOrder(String sign, FamilyGameAsset familyGameAsset) {
		// TODO Auto-generated method stub
		LOG.error("addFamilyGameOrder挂了:");
		return false;
	}

	@Override
	public boolean addFamilyGameOrderEnd(String season, String sign) {
		// TODO Auto-generated method stub
		LOG.error("addFamilyGameOrderEnd挂了:");
		return false;
	}

	@Override
	public boolean addFamilySeason(String sign, FamilySeasonAsset familySeasonAsset) {
		// TODO Auto-generated method stub
		LOG.error("addFamilySeason挂了:");
		return false;
	}

	@Override
	public boolean addFamilySeasonEnd(String season,@RequestParam(value="gameDayNum")   String gameDayNum, String sign) {
		// TODO Auto-generated method stub
		LOG.error("addFamilySeasonEnd挂了:");
		return false;
	}
 
}
