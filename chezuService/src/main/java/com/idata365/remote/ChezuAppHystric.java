package com.idata365.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAppHystric implements ChezuAppService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAppHystric.class);

	@Override
	public boolean getUserLotter(Map<String, Object> map) {
		// TODO Auto-generated method stub
		LOG.error("ChezuAppHystric  getUserLotter挂了");
		return false;
	}

	@Override
	public boolean insertUserLivenessLog(long userId, int livenessId, String sign) {
		LOG.error("ChezuAppHystric  insertUserLivenessLog挂了");
		return false;
	}


}
