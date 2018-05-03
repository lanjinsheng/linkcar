package com.idata365.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.idata365.entity.bean.AssetFamiliesPowerLogs;
import com.idata365.entity.bean.AssetUsersPowerLogs;


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
 

 
	 
 
}
