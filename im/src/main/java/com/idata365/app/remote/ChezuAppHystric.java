package com.idata365.app.remote;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAppHystric implements ChezuAppService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAppHystric.class);

	@Override
	public String getFamiliesByUserId(Long userId, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" getFamiliesByUserId 挂了  service-app-chezu");
		return null;
	}

	@Override
	public Map<String, List<Map<String,Object>>> familyUsers(Long userId, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" familyUsers 挂了  service-app-chezu");
		return null;
	}

	@Override
	public boolean sendImMsg(Long familyId, Integer familyType, Long fromUserId, Long toUserId, String msg,
			String sign) {
		// TODO Auto-generated method stub
		LOG.error(" sendImMsg 挂了  service-app-chezu");
		return false;
	}

	 
}
