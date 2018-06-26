package com.idata365.websocket;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
	//key=userId
	public static ConcurrentHashMap <String,SocketBean> socketBeanMap=new ConcurrentHashMap<String,SocketBean>();
	
	public static ConcurrentHashMap <String,String> channelIdUserMap=new ConcurrentHashMap<String,String>();
	
	public final static String globalIm="globalIm";
	public final static String auctionList="auctionList";
	public final static String auctionDoing="auctionDoing";
	
	public static Map<String,Integer> messageTypeMap=new HashMap<String,Integer>();
	static {
		messageTypeMap.put(globalIm, 1);
		messageTypeMap.put(auctionList, 2);
		messageTypeMap.put(auctionDoing, 3);
	}
	
	public static void addMoudle(String notifyMoudle,Channel channel,String userId,String keyId) {
		log.info("addMoudle===notifyMoudle:"+notifyMoudle+"==keyId:"+keyId+"==userId:"+userId);
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
		if(notifyMoudle.equals(globalIm)){
			socketBean.setImMsg(msg);
		}else if(notifyMoudle.equals(auctionList)) {
			socketBean.setAuctionListMsg(msg);
		}else if(notifyMoudle.equals(auctionDoing)) {
			log.info("key="+userId+"==setAuctionDoingMsg");
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
		if(userId!=null) {
			socketBeanMap.remove(userId);
		}
	}
	
	static String globalImMsg="{\"msgType\": \"20\",\"notifyMoudle\": \"globalIm\",\"data\": {\"msgs\": [%s]} }";
	
	public static void sendImGlobal(String json) {
		// 群发
		log.info("sendImGlobal==json="+json);
		Global.group.writeAndFlush(new TextWebSocketFrame(String.format(globalImMsg, json)));
	}
	
	public static void sendImUser(String json,String userId) {
		if(socketBeanMap.get(userId)==null) {
			return;
		}
		log.info("sendImUser==json="+json);
		SocketBean sb=socketBeanMap.get(userId);
		sb.getChannel().writeAndFlush(new TextWebSocketFrame(String.format(globalImMsg, json)));
	}
	
	static String auctionMsg="{	\"msgType\": \"30\",\"notifyMoudle\": \"auctionList\",\"data\": {\"goods\": [%s" + 
			"] } }";
	
	static String auctionDetailMsg="{\"msgType\": \"40\",\"notifyMoudle\": \"auctionDoing\",\"data\": %s}";
	public static void sendAuctionMsg(String goodsJson,String detailJson,String datas) {
		log.info("sendAuctionMsg=="+datas+"socketBeanMap.size()="+socketBeanMap.size());
		if(socketBeanMap.size()==0) return;
		Collection c=socketBeanMap.values();
		Iterator it=c.iterator();
		while(it.hasNext()) {
			SocketBean s=(SocketBean)it.next();
			if(s.getAuctionListMsg()!=null) {
				s.getChannel().writeAndFlush(new TextWebSocketFrame(String.format(auctionMsg, goodsJson)));
			}
			if(s.getAuctionDoingMsg()!=null) {
				log.info("sendAuctionMsg==s.getAuctionDoingMsg()=="+s.getAuctionDoingMsg().getDatas()+"=="+(datas));
				if(s.getAuctionDoingMsg().getDatas().toString().equals(datas)) {
					s.getChannel().writeAndFlush(new TextWebSocketFrame(String.format(auctionDetailMsg, detailJson)));
				}
			}
		}
	}
	public static void main(String []args) {
		socketBeanMap.remove(null);
	}
} 
