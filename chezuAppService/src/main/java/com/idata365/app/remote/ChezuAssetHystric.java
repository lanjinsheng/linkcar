package com.idata365.app.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAssetHystric implements ChezuAssetService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAssetHystric.class);

	@Override
	public String getUserPowerByEffectId(long effectId, String sign) {
		// TODO Auto-generated method stub
		LOG.error("getUserPowerByEffectId 挂了");
		return null;
	}
	 
	
 
}
