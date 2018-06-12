package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Prize  implements Serializable{
    
	        /**
	        * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	        */
	    
	private static final long serialVersionUID = 7939225107680860121L;

	private Long prizeId;

    private String prizeName;

    private String prizeDesc;

    private Integer isMarketable;

    private String prizePic;

    private BigDecimal originalPrice;

    private BigDecimal diamondValue;

    private String prizeDetailPics;

    private String prizeDetailTexts;
    
    private Integer stock;
    
    private Long ofUserId;

	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
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

	public Integer getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Integer isMarketable) {
		this.isMarketable = isMarketable;
	}

	public String getPrizePic() {
		return prizePic;
	}

	public void setPrizePic(String prizePic) {
		this.prizePic = prizePic;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getDiamondValue() {
		return diamondValue;
	}

	public void setDiamondValue(BigDecimal diamondValue) {
		this.diamondValue = diamondValue;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Long getOfUserId() {
		return ofUserId;
	}

	public void setOfUserId(Long ofUserId) {
		this.ofUserId = ofUserId;
	}
}