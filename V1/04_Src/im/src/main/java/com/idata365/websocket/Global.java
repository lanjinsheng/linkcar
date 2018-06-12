package com.idata365.websocket;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.idata365.app.entity.bean.Message;
import com.idata365.app.entity.bean.SocketBean;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
public class Global {
	private static Logger log = Logger.getLogger(Global.class);

	public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public static ConcurrentHashMap <String,SocketBean> socketBeanMap=new ConcurrentHashMap<String,SocketBean>();
	
	public static ConcurrentHashMap <String,String> channelIdUserMap=new ConcurrentHashMap<String,String>();
	
	public final static String globalIm="globalIm";
	public final static String auctionList="auctionList";
	public final static String auctionDoing="auctionDoing";
	
	public static Map<String,Integer> messageTypeMap=new HashMap<String,Integer>();
	static {
		messageTypeMap.put(globalIm, 1);
		messageTypeMap.put(auctionList, 2);
		messageTypeMap.put(auctionDoing, 2);
	}
	
	public static void addMoudle(String notifyMoudle,Channel channel,String userId,String keyId) {
		SocketBean socketBean=null;
		if(socketBeanMap.get(userId)==null) {
			  socketBean=new SocketBean(channel);
			  socketBeanMap.put(userId, socketBean);
		}else {
			  socketBean=socketBeanMap.get(userId);
		}
		Message msg=new Message();
		msg.setMessageType(messageTypeMap.get(notifyMoudle));
		msg.setDatas(keyId);
		if(notifyMoudle.equalsIgnoreCase(globalIm)){
			socketBean.setImMsg(msg);
		}else if(notifyMoudle.equalsIgnoreCase(auctionList)) {
			socketBean.setAuctionListMsg(msg);
		}else if(notifyMoudle.equalsIgnoreCase(auctionDoing)) {
			socketBean.setAuctionDoingMsg(msg);
		}
	}
	public static void removeMoudle(String notifyMoudle,String userId) {
		if(socketBeanMap.get(userId)==null) {
			return;
		} 
		SocketBean  socketBean=socketBeanMap.get(userId);
		if(notifyMoudle.equalsIgnoreCase(globalIm)){
			socketBean.setImMsg(null);
		}else if(notifyMoudle.equalsIgnoreCase(auctionList)) {
			socketBean.setAuctionListMsg(null);
		}else if(notifyMoudle.equalsIgnoreCase(auctionDoing)) {
			socketBean.setAuctionDoingMsg(null);
		}
	}
	public static void mapChannelUser(String channelId,String userId) {
		channelIdUserMap.put(channelId, userId);
	}
	
	public static void inActiveChannel(String channelId) {
		String userId=channelIdUserMap.get(channelId);
		channelIdUserMap.remove(channelId);
		socketBeanMap.remove(userId);
	}
	
	static String globalImMsg="{\"msgType\": \"20\",\"notifyMoudle\": \"globalIm\",\"data\": {\"msgs\": [%s]} }";
	
	public static void sendImGlobal(String json) {
		// 群发
		Global.group.writeAndFlush(new TextWebSocketFrame(String.format(globalImMsg, json)));
	}
	
} 
