package com.idata365.app.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.bean.UserInfo;


@Component
public class ChezuHystric implements ChezuService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuHystric.class);

	@Override
	public UsersAccount validToken(String token) {
		// TODO Auto-generated method stub
		LOG.info(" validToken 挂了   service-chezu");
		return null;
	}

	@Override
	public UserInfo getUserInfoByToken(String token) {
		// TODO Auto-generated method stub
		LOG.info(" getUserInfoByToken 挂了   service-chezu");
		return null;
	}
 
	
 
}
