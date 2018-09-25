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
	public boolean sendAuctionMsg(Long auctionGoodsId, Integer auctionGoodsType, Integer eventType, String userIds,
			String goodsName, String sign) {
		// TODO Auto-generated method stub
		LOG.error("sendAuctionMsg==userIds="+userIds+"竞拍推送消息"+goodsName+" sign="+sign+"消息发送失败");
		return false;
	}

	@Override
	public boolean sendAuctionRobbedMsg(Long userId, String goodsName, String sign) {
		LOG.error("sendAuctionMsg==userIds="+userId+"竞拍被抢推送消息"+goodsName+" sign="+sign+"消息发送失败");
		return false;
	}

	@Override
	public boolean sendNoticeMsgToFailedAuctionPerson(String userIds, String goodsName, String sign) {
		LOG.error("sendAuctionMsg==userIds="+userIds+"竞拍发布新内容"+goodsName+" sign="+sign+"消息发送失败");
		return false;
	}


}
