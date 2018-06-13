package com.idata365.app.controller.open;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.entity.bean.AuctionBean;
import com.idata365.app.service.ImService;
import com.idata365.app.util.DateTools;

@RestController
public class NotifyController extends BaseController{
	protected static final Logger LOG = LoggerFactory.getLogger(NotifyController.class);
	@Autowired
	ImService imService;
	 
	/**
	 * 
	    * @Title: notifyAuction
	    * @Description: TODO(进行竞拍信息的通知)
	    * @param @param auctionBean
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
 
	@RequestMapping(value = "/im/notifyAuction",method = RequestMethod.POST)
	public Map<String,Object>  notifyAuction(@RequestBody AuctionBean auctionBean,String auctionPerson,String auctionTimes)
	{
//		LOG.info("userId="+userId+"===sign="+sign);
		Map<String,String> goodsList=new HashMap<String,String>();
		goodsList.put("goodsId", String.valueOf(auctionBean.getAuctionGoods().getAuctionGoodsId()));
		goodsList.put("endTime", DateTools.getDateTime(auctionBean.getAuctionGoods().getAuctionRealEndTime()));
//		goodsList.put("auctionTime", String.valueOf(auctionBean.getAuctionGoods().getAuctionEndTime()));
		goodsList.put("auctionDiamond", String.valueOf(auctionBean.getAuctionGoods().getDoneDiamond().doubleValue()));
		goodsList.put("isEnd", String.valueOf(auctionBean.getAuctionGoods().getAuctionStatus()));
		
		 
 
		Map<String,Object> notifyInfo=new HashMap<String,Object>();
		notifyInfo.put("goodsId", String.valueOf(auctionBean.getAuctionGoods().getAuctionGoodsId()));
		notifyInfo.put("endTime", DateTools.getDateTime(auctionBean.getAuctionGoods().getAuctionRealEndTime()));
//		notifyInfo.put("auctionTime", String.valueOf(auctionBean.getAuctionGoods().getAuctionEndTime()));
		notifyInfo.put("auctionTimes", auctionTimes);
		notifyInfo.put("auctionPerson", auctionPerson);
		notifyInfo.put("auctionDiamond", String.valueOf(auctionBean.getAuctionGoods().getDoneDiamond().doubleValue()));
		notifyInfo.put("isEnd", String.valueOf(auctionBean.getAuctionGoods().getAuctionStatus()));
		
		List<Map<String,String>> actionLogs=new ArrayList<Map<String,String>>();
		List<AuctionLogs> list=auctionBean.getAuctionLogsList();
		for(AuctionLogs log:list) {
			Map<String,String> logMap=new HashMap<String,String>();
			logMap.put("nick", log.getAuctionUserNick());
			logMap.put("auctionTime", DateTools.getDateTime(log.getAuctionTime()));
			logMap.put("auctionDiamond", String.valueOf(log.getAuctionDiamond().doubleValue()));
			actionLogs.add(logMap);
		}
		notifyInfo.put("actionLogs", actionLogs);
		imService.sendAuctionMsg(goodsList,notifyInfo);
	    LOG.info(auctionBean.getAuctionGoods().getPrizeName());
		return null;
	}
	 
}
