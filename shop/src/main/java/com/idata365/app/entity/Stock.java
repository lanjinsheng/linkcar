package com.idata365.app.entity;

import java.io.Serializable;

public class Stock implements Serializable {
    
	        /**
	        * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	        */
	    
	private static final long serialVersionUID = -3936032475523305363L;

	private Long stockid;

    private String prizename;

    private Integer parizenum;

    private Long providerid;

    public Long getStockid() {
        return stockid;
    }

    public void setStockid(Long stockid) {
        this.stockid = stockid;
    }

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename == null ? null : prizename.trim();
    }

    public Integer getParizenum() {
        return parizenum;
    }

    public void setParizenum(Integer parizenum) {
        this.parizenum = parizenum;
    }

    public Long getProviderid() {
        return providerid;
    }

    public void setProviderid(Long providerid) {
        this.providerid = providerid;
    }
}