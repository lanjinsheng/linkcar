package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionLogs implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 7934225107680850121L;

	private Long auctionLogsId;
	private Long auctionGoodsId;
	private Long auctionUserId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date auctionTime;
	private BigDecimal auctionDiamond;
	private String auctionUserNick;
	
	public Long getAuctionLogsId() {
		return auctionLogsId;
	}

	public void setAuctionLogsId(Long auctionLogsId) {
		this.auctionLogsId = auctionLogsId;
	}

	public Long getAuctionGoodsId() {
		return auctionGoodsId;
	}

	public void setAuctionGoodsId(Long auctionGoodsId) {
		this.auctionGoodsId = auctionGoodsId;
	}

	public Long getAuctionUserId() {
		return auctionUserId;
	}

	public void setAuctionUserId(Long auctionUserId) {
		this.auctionUserId = auctionUserId;
	}

	public Date getAuctionTime() {
		return auctionTime;
	}

	public void setAuctionTime(Date auctionTime) {
		this.auctionTime = auctionTime;
	}

	public BigDecimal getAuctionDiamond() {
		return auctionDiamond;
	}

	public void setAuctionDiamond(BigDecimal auctionDiamond) {
		this.auctionDiamond = auctionDiamond;
	}

	public String getAuctionUserNick() {
		return auctionUserNick;
	}

	public void setAuctionUserNick(String auctionUserNick) {
		this.auctionUserNick = auctionUserNick;
	}

}