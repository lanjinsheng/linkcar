package com.idata365.app.service;

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
	public void sendAuctionMsg(Map<String,String> goods,Map<String,Object> goodDetail) {
		Global.sendAuctionMsg(GsonUtils.toJson(goods, false), GsonUtils.toJson(goodDetail, false));
	}
	
	public List<Map<String,String>>  getMsgs()
	{
		return imMapper.getMsg();
	} 
	
	
}
