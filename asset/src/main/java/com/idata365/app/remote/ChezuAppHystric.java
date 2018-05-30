package com.idata365.app.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


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
	public void updateHadNewPower(long userId, long familyId, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" updateHadNewPower 挂了  service-app-chezu");
	}

}
