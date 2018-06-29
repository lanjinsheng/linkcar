package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChezuAccountHystric implements ChezuAccountService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAccountHystric.class);

	@Override
	public Map<String, String> isAuthenticated(long userId, String sign) {
		LOG.error("isAuthenticated挂了account");
		return null;
	}

	@Override
	public int queryCountOfIdcard(long userId, String sign) {
		LOG.error("queryCountOfIdcard挂了account");
		return 0;
	}

	@Override
	public int queryCountOfLicense(long userId, String sign) {
		LOG.error("queryCountOfLicense挂了account");
		return 0;
	}

	@Override
	public String getCurrentUsersByFamilyId(long familyId, String daystamp, String sign) {
		LOG.error("getCurrentUsersByFamilyId挂了account");
		return null;
	}


}
