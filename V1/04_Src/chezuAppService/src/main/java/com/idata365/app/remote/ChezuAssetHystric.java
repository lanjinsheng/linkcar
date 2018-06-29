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
	public List<Map<String, String>> billBoard(String billBoardType, long userId, String sign) {
		// TODO Auto-generated method stub
		LOG.error("billBoard 挂了");
		return null;
	}

	@Override
	public long getFamilySeasonID(String daystamp, long myFamilyId, String sign) {
		LOG.error("getFamilySeasonID 挂了");
		return 0;
	}

	@Override
	public Map<Long, String> getUsersAssetMap(String userIds, String sign) {
		// TODO Auto-generated method stub
		LOG.error("getUsersAssetMap 挂了");
		return null;
	}

	@Override
	public String queryHaveNewPower(long userId, long familyId, String sign) {
		LOG.error("queryHaveNewPower 挂了");
		return null;
	}

	@Override
	public boolean getMissionPrize(long userId, int powerPrize, int missionId, String sign) {
		LOG.error("getMissionPrize 挂了");
		return false;
	}

	@Override
	public Map<String, String> reducePowersByChallege(long userId,
			  String sign) {
		// TODO Auto-generated method stub
		LOG.error("reducePowersByChallege 挂了");
		return null;
	}

	@Override
	public int queryCountOfSteal(long userId, String sign) {
		LOG.error("queryCountOfSteal 挂了");
		return 0;
	}
 
}
