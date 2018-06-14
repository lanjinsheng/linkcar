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
		LOG.error("userId="+userId+"兑换"+goodsName+" sign="+sign+"消息发送失败");
		return false;
	}

	@Override
	public boolean sendGoodsSendMsg(Long userId, String goodsName, String sign) {
		// TODO Auto-generated method stub
		LOG.error("userId="+userId+"兑换"+goodsName+" sign="+sign+"消息发送失败");
		return false;
	}

	@Override
	public boolean verifyIDCardMsg(Long userId, String userName, String cardNumber, String sign) {
		LOG.error("userId="+userId+"userName="+userName+"身份证号"+cardNumber+" sign="+sign+"消息发送失败");
		return false;
	}
 
 
}
