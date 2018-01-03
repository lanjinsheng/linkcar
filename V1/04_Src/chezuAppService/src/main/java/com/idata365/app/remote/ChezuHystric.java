package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuHystric implements ChezuService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuHystric.class);
	@Override
	public boolean updateUserDevice( Map<String,Object> map) {
		// TODO Auto-generated method stub
		LOG.info("updateUserDevice 挂了 service-chezu");
		return false;
	}
	@Override
	public Map<String, Object> getGpsByUH(Long userId, Long habitId, String sign) {
		// TODO Auto-generated method stub
		LOG.info(" getGpsByUH 挂了   service-chezu");
		return null;
	}
 
}
