package com.idata365.app.entity.bean;

import io.netty.channel.Channel;

public class SocketBean {
	private Message imMsg;
	private Message auctionListMsg;
	private Message auctionDoingMsg;
	
	public SocketBean( Channel channel) {
		this.channel=channel;
	}
	private Channel channel;
	
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public Message getImMsg() {
		return imMsg;
	}
	public void setImMsg(Message imMsg) {
		this.imMsg = imMsg;
	}
	public Message getAuctionListMsg() {
		return auctionListMsg;
	}
	public void setAuctionListMsg(Message auctionListMsg) {
		this.auctionListMsg = auctionListMsg;
	}
	public Message getAuctionDoingMsg() {
		return auctionDoingMsg;
	}
	public void setAuctionDoingMsg(Message auctionDoingMsg) {
		this.auctionDoingMsg = auctionDoingMsg;
	}
	
	
}
