package com.idata365.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
    * @ClassName: ImService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.mapper.ImMapper;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.GsonUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.websocket.Global;
 

@Service
public class ImService extends BaseService<ImService>
{
	private final static Logger LOG = LoggerFactory.getLogger(ImService.class);
	@Autowired
	ImMapper imMapper;
	@Autowired
	ChezuAppService chezuAppService;
	 /**
	  * 
	     * @Title: insertMsg
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param @param userIds
	     * @param @return    参数
	     * @return Map<String,Object>    返回类型
	     * @throws
	     * @author LanYeYe
	  */
	@Transactional
	public void  insertMsg(Map<String,Object> msg)
	{
		imMapper.insert(msg);
	}
	/**
	 * 
	    * @Title: sendGloadIm
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param msg    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public void sendGloadIm(Map<String,Object> msg) {
		Global.sendImGlobal(GsonUtils.toJson(msg, false));
	}
	public void sendUserFamilyIm(Map<String,Object> msg,String userId,int type) {
		String sign=SignUtils.encryptHMAC(userId);
		Map<String, Object> rtMap=chezuAppService.familyUsers(Long.valueOf(userId), sign);
		msg.put("type", "1");
		String msg1=GsonUtils.toJson(msg, false);
		msg.put("type", "2");
		String msg2=GsonUtils.toJson(msg, false);
		String atUsers=msg.get("atUsers")==null?null:String.valueOf(msg.get("atUsers"))+",";
		
		List<Map<String,Object>> list=null;
		if(type==1) {
		    list=(List<Map<String,Object>>)rtMap.get("createFamily");
			
		}else if(type==2) {
			list=(List<Map<String,Object>>)rtMap.get("partakeFamily");
		}
		if(list!=null) {
			for(Map<String,Object> m:list) {
				String id=String.valueOf(m.get("userId"));
				if(m.get("isLeader").equals(1)) {
					Global.sendImUser(msg1,id);
					if(atUsers!=null && atUsers.contains(id+",")) {
						String notifySign=SignUtils.encryptHMAC(id);
						chezuAppService.sendImMsg(Long.valueOf(msg.get("familyId").toString()), 1, Long.valueOf(userId), Long.valueOf(id),
								String.valueOf(msg.get("msg")), notifySign);
					}
				}else {
					Global.sendImUser(msg2,id);
					if(atUsers!=null && atUsers.contains(id+",")) {
						String notifySign=SignUtils.encryptHMAC(id);
						chezuAppService.sendImMsg(Long.valueOf(msg.get("familyId").toString()), 2, Long.valueOf(userId), Long.valueOf(id),
								String.valueOf(msg.get("msg")), notifySign);
					}
				}
			}
		}
	}
	public void sendUserFamilyImByPraying(Map<String,Object> msg,Map<String, Object> familiesMap,int type) {

		msg.put("type", 1);
		String msg1=GsonUtils.toJson(msg, false);
		msg.put("type", 2);
		String msg2=GsonUtils.toJson(msg, false);
		
		List<Map<String,Object>> list=null;
		if(type==1) {//创建家族
		    list=(List<Map<String,Object>>)familiesMap.get("createFamily");
			
		}else if(type==2) {//参与家族
			list=(List<Map<String,Object>>)familiesMap.get("partakeFamily");
		}
		if(list!=null) {
			for(Map<String,Object> m:list) {
				String id=String.valueOf(m.get("userId"));
				if(m.get("isLeader").equals(1)) {
					Global.sendImUser(msg1,id);
				}else {
					Global.sendImUser(msg2,id);
				}
			}
		}
	}
	public void changeFamiliesUsersIm(List<Map<String,Object>> list) {
//		String json=GsonUtils.toJson(msg, false);
		for(Map<String,Object> m:list) {
			String id=String.valueOf(m.get("userId"));
			Global.sendImUserChangeFamiliesMember("",id);
		}

	}
	
	/**
	 * 
	    * @Title: sendAuctionMsg
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param goods
	    * @param @param goodDetail    参数
	    * @return void    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public void sendAuctionMsg(Map<String,String> goods,Map<String,Object> goodDetail,String keyId) {
		Global.sendAuctionMsg(GsonUtils.toJson(goods, false), GsonUtils.toJson(goodDetail, false),keyId);
	}
	
	public  Map<String,List<Map<String,String>>>   getMsgs(String baseUrl,Long familyId,Long partakeFamilyId)
	{
		Map<String,List<Map<String,String>>> rtMap=new HashMap<String,List<Map<String,String>>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("msgType", 0);
		map.put("familyId", 0);
		map.put("id", 9999999999999999L);
		map.put("imgPath", baseUrl);
		List<Map<String,String>> list1=imMapper.getMsgGlobal(map);
		if(list1==null) {	list1=new ArrayList<Map<String,String>>();}
		rtMap.put("0", list1);
		List<Map<String,String>> list2=null;
		List<Map<String,String>> list3=null;
		if(familyId>0) {
			map.put("familyId", familyId);
			map.put("msgType", 1);
			list2=imMapper.getMsg(map);
			
		}
		if(partakeFamilyId>0) {
			map.put("familyId", partakeFamilyId);
			map.put("msgType", 2);
			list3=imMapper.getMsg(map);
			 
		}
		if(list2==null) {list2=new ArrayList<Map<String,String>>();}
		rtMap.put("1", list2);
		if(list3==null) {list3=new ArrayList<Map<String,String>>();}
		rtMap.put("2", list3);
		return rtMap;
	} 
	
	@Transactional
	public void insertInLog(String userId,String channelId) {
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		map.put("channelId", channelId);
		map.put("inTime", System.currentTimeMillis());
		map.put("inTimeStr", DateTools.getCurDate());
		map.put("offTime",0);
		map.put("offTimeStr", "");
		map.put("onlineTime",-1);
		imMapper.insertLog(map);
	}
	@Transactional
	public void updateOffLog(String channelId) {
		Map<String,Object> map=new HashMap<>();
		map.put("channelId", channelId);
		map.put("offTime",System.currentTimeMillis());
		map.put("offTimeStr", DateTools.getCurDate());
		imMapper.updateLog(map);
	}
	
public static void main(String []args) {
	Map<String,Object> map=new HashMap<String,Object>();
	Integer a=1;
	map.put("aaa", a);
	if(a.equals(1)) {
		System.out.println("if:"+map.get("aaa"));
	}else {
		System.out.println("else:"+map.get("aaa"));
	}
}
}
