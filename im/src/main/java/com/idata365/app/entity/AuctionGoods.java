package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionGoods implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 7939225107680850121L;

	private Long auctionGoodsId;
	private String prizeName;
	private String prizeDesc;
	private String prizePic;
	private BigDecimal startDiamond;
	private BigDecimal doneDiamond;
	private Long winnerId;
	private String winnerName;
	private String prizeDetailPics;
	private String prizeDetailTexts;
	private Long ofUserId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date auctionStartTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date auctionEndTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date auctionRealEndTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	private BigDecimal stepPrice;
	private Integer auctionStatus;
	private String remark;

	public String getWinnerName() {
		return winnerName;
	}

	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}

	public Long getAuctionGoodsId() {
		return auctionGoodsId;
	}

	public void setAuctionGoodsId(Long auctionGoodsId) {
		this.auctionGoodsId = auctionGoodsId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getPrizeDesc() {
		return prizeDesc;
	}

	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}

	public String getPrizePic() {
		return prizePic;
	}

	public void setPrizePic(String prizePic) {
		this.prizePic = prizePic;
	}

	public BigDecimal getStartDiamond() {
		return startDiamond;
	}

	public void setStartDiamond(BigDecimal startDiamond) {
		this.startDiamond = startDiamond;
	}

	public BigDecimal getDoneDiamond() {
		return doneDiamond;
	}

	public void setDoneDiamond(BigDecimal doneDiamond) {
		this.doneDiamond = doneDiamond;
	}

	public Long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}

	public String getPrizeDetailPics() {
		return prizeDetailPics;
	}

	public void setPrizeDetailPics(String prizeDetailPics) {
		this.prizeDetailPics = prizeDetailPics;
	}

	public String getPrizeDetailTexts() {
		return prizeDetailTexts;
	}

	public void setPrizeDetailTexts(String prizeDetailTexts) {
		this.prizeDetailTexts = prizeDetailTexts;
	}

	public Long getOfUserId() {
		return ofUserId;
	}

	public void setOfUserId(Long ofUserId) {
		this.ofUserId = ofUserId;
	}

	public Date getAuctionStartTime() {
		return auctionStartTime;
	}

	public void setAuctionStartTime(Date auctionStartTime) {
		this.auctionStartTime = auctionStartTime;
	}

	public Date getAuctionEndTime() {
		return auctionEndTime;
	}

	public void setAuctionEndTime(Date auctionEndTime) {
		this.auctionEndTime = auctionEndTime;
	}

	public Date getAuctionRealEndTime() {
		return auctionRealEndTime;
	}

	public void setAuctionRealEndTime(Date auctionRealEndTime) {
		this.auctionRealEndTime = auctionRealEndTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getStepPrice() {
		return stepPrice;
	}

	public void setStepPrice(BigDecimal stepPrice) {
		this.stepPrice = stepPrice;
	}

	public Integer getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(Integer auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}