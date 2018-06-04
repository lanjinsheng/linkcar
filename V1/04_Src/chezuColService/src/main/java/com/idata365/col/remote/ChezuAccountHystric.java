package com.idata365.col.remote;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChezuAccountHystric implements ChezuAccountService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAccountHystric.class);

	@Override
	public long getUserIdByPhone(String phone, String sign) {
		// TODO Auto-generated method stub
		LOG.error("getUserIdByPhone 挂了");
		return 0;
	}

	 

}
