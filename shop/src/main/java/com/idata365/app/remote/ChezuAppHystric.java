package com.idata365.app.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class ChezuAppHystric implements ChezuAppService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuAppHystric.class);

	@Override
	public boolean sendShopMsg(Long userId, String goodsName, String sign) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendGoodsSendMsg(Long userId, String goodsName, String sign) {
		// TODO Auto-generated method stub
		return false;
	}
 
 
}
