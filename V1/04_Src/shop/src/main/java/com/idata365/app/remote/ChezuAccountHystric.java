package com.idata365.app.remote;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.idata365.app.entity.UsersAccount;

@Component
public class ChezuAccountHystric implements ChezuAccountService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAccountHystric.class);

	@Override
	public Map<String, String> isAuthenticated(long userId, String sign) {
		LOG.error("isAuthenticated挂了account");
		return null;
	}


}
