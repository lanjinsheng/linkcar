package com.idata365.app.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAppHystric implements ChezuAppService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAppHystric.class);


	@Override
	public boolean sendFamilyDiamondsMsg(String season, String familyId, String orderNum, Long toUserId,
			String diamondNum, String sign) {
		LOG.error(" sendFamilyDiamondsMsg 挂了  service-app-chezu");
		return false;
	}


	@Override
	public boolean sendFamilyDiamondsSeasonMsg(String season, String familyId, Integer familyType, Long toUserId,
			String diamondNum, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" sendFamilyDiamondsSeasonMsg 挂了  service-app-chezu");
		return false;
	}

}
