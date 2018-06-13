package com.idata365.app.entity.bean;

import java.io.Serializable;
import java.util.List;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.AuctionLogs;

public class AuctionBean  implements Serializable{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = -2684735878652236273L;
	private AuctionGoods auctionGoods;
	private List<AuctionLogs> auctionLogsList;
	public AuctionGoods getAuctionGoods() {
		return auctionGoods;
	}
	public void setAuctionGoods(AuctionGoods auctionGoods) {
		this.auctionGoods = auctionGoods;
	}
	public List<AuctionLogs> getAuctionLogsList() {
		return auctionLogsList;
	}
	public void setAuctionLogsList(List<AuctionLogs> auctionLogsList) {
		this.auctionLogsList = auctionLogsList;
	}
	 
	
}
