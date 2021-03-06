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
	public int queryCountOfSteal(long userId, String sign) {
		LOG.error("queryCountOfSteal 挂了");
		return 0;
	}

	@Override
	public Map<String, String> reducePowersByChallege(long userId,
			int challegeTimesToday, String sign) {
		// TODO Auto-generated method stub
		LOG.error("reducePowersByChallege 挂了");
		return null;
	}

	@Override
	public Map<String, String> reducePowersByPeccancy(long userId,
			long payerId, int type, int power, long effectId, String sign) {
		// TODO Auto-generated method stub
		LOG.error("reducePowersByPeccancy 挂了");
		return null;
	}

	@Override
	public int queryHadGetBonus(long userId, String sign) {
		LOG.error("queryHadGetBonus 挂了");
		return 0;
	}

	@Override
	public boolean receiveClubBonus(long userId, long powerNum, String sign) {
		LOG.error("receiveClubBonus 挂了");
		return false;
	}

	@Override
	public boolean receiveDayMissionBox(long userId, long powerNum, String sign) {
		LOG.error("receiveDayMissionBox 挂了");
		return false;
	}

	@Override
	public int queryReceiveDayMissionBox(long userId, String sign) {
		LOG.error("queryReceiveDayMissionBox 挂了");
		return 0;
	}

	@Override
	public boolean receiveActMissionBox(long userId, long powerNum, String sign) {
		LOG.error("receiveActMissionBox 挂了");
		return false;
	}

	@Override
	public int queryReceiveActMissionBox(long userId, String sign) {
		LOG.error("queryReceiveActMissionBox 挂了");
		return 0;
	}

	@Override
	public long queryMaxActPowerByTime(String daystamp, String sign) {
		LOG.error("queryMaxActPowerByTime 挂了");
		return 0;
	}

	@Override
	public long queryMaxActPowerByTimeAndUserId(String daystamp, long userId, String sign) {
		LOG.error("queryMaxActPowerByTimeAndUserId 挂了");
		return 0;
	}
 
}
