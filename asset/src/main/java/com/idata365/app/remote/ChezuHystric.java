package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.idata365.app.entity.UsersAccount;

@Component
public class ChezuHystric implements ChezuService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuHystric.class);

	@Override
	public UsersAccount validToken(String token) {
		// TODO Auto-generated method stub
		LOG.info(" validToken 挂了  service-account-chezu");
		return null;
	}


	@Override
	public Map<String, Object> getFamiliesInfoByUserId(long userId, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" getFamiliesInfoByUserId 挂了  service-account-chezu");
		return null;
	}


	@Override
	public Map<String, Object> getUsersInfoByIds(String userIds, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" getUsersInfoByIds 挂了  service-account-chezu");
		return null;
	}

}
