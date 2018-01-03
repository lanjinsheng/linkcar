package com.idata365.col.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChezuServiceHystric implements ChezuDriveService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuServiceHystric.class);

	@Override
	public Map<String, Object> getScoreByUH(Long userId, Long habitId, String sign) {
		// TODO Auto-generated method stub
		LOG.info("ChezuServiceHystric 调用错误");
		return null;
	}
	 
 
}
