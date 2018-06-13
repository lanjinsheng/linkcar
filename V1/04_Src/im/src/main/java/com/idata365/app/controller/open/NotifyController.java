package com.idata365.app.controller.open;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.bean.AuctionBean;
import com.idata365.app.service.ImService;

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
	public Map<String,Object>  notifyAuction(@RequestBody AuctionBean auctionBean)
	{
//		LOG.info("userId="+userId+"===sign="+sign);
	    LOG.info(auctionBean.getAuctionGoods().getPrizeName());
		return null;
	}
	 
}
