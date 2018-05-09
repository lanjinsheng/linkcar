package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuSmsHystric implements ChezuSmsService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuSmsHystric.class);

	@Override
	public boolean sendMessage(Map<String, String> map) {
		// TODO Auto-generated method stub
		LOG.info(" sendMessage 挂了   service-sms");
		return false;
	}

	@Override
	public boolean sendMessageTest() {
		// TODO Auto-generated method stub
		LOG.info(" sendMessageTest 挂了   service-sms");
		return false;
	}
	
 
}
