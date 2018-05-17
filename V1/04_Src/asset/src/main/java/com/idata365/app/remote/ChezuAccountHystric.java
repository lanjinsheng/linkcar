package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.idata365.app.entity.UsersAccount;

@Component
public class ChezuAccountHystric implements ChezuAccountService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAccountHystric.class);

	@Override
	public UsersAccount validToken(String token) {
		// TODO Auto-generated method stub
		LOG.info(" validToken 挂了  service-account-chezu");
		return null;
	}


	@Override
	public Map<String, Object> getUsersInfoByIds(String userIds, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" getUsersInfoByIds 挂了  service-account-chezu");
		return null;
	}


	@Override
	public String getUsersByFamilyId(long familyId, String daystamp, String sign) {
		// TODO Auto-generated method stub
		LOG.error(" getUsersByFamilyId 挂了  service-account-chezu");
		return null;
	}


	@Override
	public Map<String, Object> getFamiliesInfoByfamilyId(long familyId, String sign) {
		LOG.error(" getFamiliesInfoByfamilyId 挂了  service-account-chezu");
		return null;
	}


	@Override
	public boolean updateLoginTime(long userId, String sign) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getCurrentUsersByFamilyId(long familyId, String daystamp, String sign) {
		LOG.error(" getCurrentUsersByFamilyId 挂了  service-account-chezu");
		// TODO Auto-generated method stub
		return null;
	}

}
