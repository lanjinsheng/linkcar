package com.idata365.app.remote;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAssetHystric implements ChezuAssetService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAssetHystric.class);

	@Override
	public String getUserPowerByEffectId(long effectId, String sign) {
		// TODO Auto-generated method stub
		LOG.error("getUserPowerByEffectId 挂了");
		return null;
	}

	@Override
	public boolean initUserCreate(long userId, String sign) {
		// TODO Auto-generated method stub
		LOG.error("initUserCreate 挂了");
		return false;
	}

	@Override
	public boolean initFamilyCreate(long familyId, String sign) {
		// TODO Auto-generated method stub
		LOG.error("initFamilyCreate 挂了");
		return false;
	}

	@Override
	public List<Map<String, String>> billBoard(String billBoardType, String sign) {
		LOG.error("billBoard 挂了");
		return null;
	}
	 
	
 
}
