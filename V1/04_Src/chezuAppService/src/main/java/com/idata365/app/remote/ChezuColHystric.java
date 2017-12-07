package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuColHystric implements ChezuColService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuColHystric.class);
	@Override
	public boolean updateUserDevice( Map<String,Object> map) {
		// TODO Auto-generated method stub
		LOG.info("挂了 service-col-chezu");
		return false;
	}
 
}
