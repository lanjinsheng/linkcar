package com.idata365.app.remote;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.idata365.app.entity.bean.AuctionBean;

@Component
public class ChezuImHystric implements ChezuImService {
	private final static Logger LOG = LoggerFactory.getLogger(ChezuImHystric.class);

	@Override
	public Map<String, Object> notifyAuction(AuctionBean auctionBean, String auctionPerson, String auctionTimes) {
		// TODO Auto-generated method stub
		LOG.error("notifyAuction 挂了");
		return null;
	}

}
