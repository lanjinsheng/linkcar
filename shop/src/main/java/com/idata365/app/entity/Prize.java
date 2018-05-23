package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Prize  implements Serializable{
    
	        /**
	        * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	        */
	    
	private static final long serialVersionUID = 7939225107680860121L;

	private Long prizeid;

    private String prizename;

    private String prizedesc;

    private Integer isMarketable;

    private String prizepic;

    private BigDecimal originalprice;

    private Integer diamondvalue;

    private String prizedetailpics;

    private String prizedetailtexts;
    
    private Integer stock;

    public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Long getPrizeid() {
        return prizeid;
    }

    public void setPrizeid(Long prizeid) {
        this.prizeid = prizeid;
    }

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename == null ? null : prizename.trim();
    }

    public String getPrizedesc() {
        return prizedesc;
    }

    public void setPrizedesc(String prizedesc) {
        this.prizedesc = prizedesc == null ? null : prizedesc.trim();
    }

    public Integer getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Integer isMarketable) {
        this.isMarketable = isMarketable;
    }

    public String getPrizepic() {
        return prizepic;
    }

    public void setPrizepic(String prizepic) {
        this.prizepic = prizepic == null ? null : prizepic.trim();
    }

    public BigDecimal getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(BigDecimal originalprice) {
        this.originalprice = originalprice;
    }

    public Integer getDiamondvalue() {
        return diamondvalue;
    }

    public void setDiamondvalue(Integer diamondvalue) {
        this.diamondvalue = diamondvalue;
    }

    public String getPrizedetailpics() {
        return prizedetailpics;
    }

    public void setPrizedetailpics(String prizedetailpics) {
        this.prizedetailpics = prizedetailpics == null ? null : prizedetailpics.trim();
    }

    public String getPrizedetailtexts() {
        return prizedetailtexts;
    }

    public void setPrizedetailtexts(String prizedetailtexts) {
        this.prizedetailtexts = prizedetailtexts == null ? null : prizedetailtexts.trim();
    }
}