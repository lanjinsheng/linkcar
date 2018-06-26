package com.idata365.app.remote;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAppHystric implements ChezuAppService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAppHystric.class);


	@Override
	public boolean sendFamilyDiamondsMsg(String season, String familyId, String orderNum, Long toUserId,
			String diamondNum, String personDiamondNum,String sign) {
		LOG.error(" sendFamilyDiamondsMsg 挂了  service-app-chezu");
		return false;
	}


	@Override
	public boolean sendFamilyDiamondsSeasonMsg(String season, String familyId, Integer familyType, Long toUserId,
			String diamondNum, String personDiamondNum,String sign) {
		// TODO Auto-generated method stub
		LOG.error(" sendFamilyDiamondsSeasonMsg 挂了  service-app-chezu");
		return false;
	}


	@Override
	public boolean updateLoginBss(long userId, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" updateLoginBss 挂了  service-app-chezu");
		return false;
	}


	@Override
	public Map<String, Object> getFightRelationAsset(Long famiyId, String sign) {
		LOG.error(" getFightRelationAsset 挂了  service-app-chezu");
		return null;
	}


	@Override
	public boolean sendFamilyPowerMsg(String season, String familyId, String orderNum, Long toUserId, String powerNum,
			String personPowerNum, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" sendFamilyPowerMsg 挂了  service-app-chezu");
		return false;
	}

}
