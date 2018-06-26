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
import com.idata365.app.util.GsonUtils;
import com.idata365.websocket.Global;
 

@Service
public class ImService extends BaseService<ImService>
{
	private final static Logger LOG = LoggerFactory.getLogger(ImService.class);
	@Autowired
	ImMapper imMapper;

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
	public void sendUserFamilyIm(Map<String,Object> msg,String userId) {
		Global.sendImUser(GsonUtils.toJson(msg, false),userId);
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
		List<Map<String,String>> list1=imMapper.getMsg(map);
		if(list1==null) {	list1=new ArrayList<Map<String,String>>();}
		rtMap.put("0", list1);
		List<Map<String,String>> list2=null;
		List<Map<String,String>> list3=null;
		if(familyId>0) {
			map.put("familyId", familyId);
			list2=imMapper.getMsg(map);
			
		}
		if(partakeFamilyId>0) {
			map.put("familyId", partakeFamilyId);
			list3=imMapper.getMsg(map);
			 
		}
		if(list2==null) {list2=new ArrayList<Map<String,String>>();}
		rtMap.put("1", list2);
		if(list3==null) {list3=new ArrayList<Map<String,String>>();}
		rtMap.put("2", list3);
		return rtMap;
	} 
	
	
}
