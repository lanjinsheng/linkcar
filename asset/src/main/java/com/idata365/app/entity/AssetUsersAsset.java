package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
    * @ClassName: AssetUsersAsset
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月27日
    *
 */
public class AssetUsersAsset implements Serializable {
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 6355124959149280568L;
	private Long id;
	private Long userId;
	private Long powerNum;
	private Long hadPowerNum;
	private BigDecimal diamondsNum;
	private BigDecimal hadDiamondsNum;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPowerNum() {
		return powerNum;
	}
	public void setPowerNum(Long powerNum) {
		this.powerNum = powerNum;
	}
	public Long getHadPowerNum() {
		return hadPowerNum;
	}
	public void setHadPowerNum(Long hadPowerNum) {
		this.hadPowerNum = hadPowerNum;
	}
	public BigDecimal getDiamondsNum() {
		return diamondsNum;
	}
	public void setDiamondsNum(BigDecimal diamondsNum) {
		this.diamondsNum = diamondsNum;
	}
	public BigDecimal getHadDiamondsNum() {
		return hadDiamondsNum;
	}
	public void setHadDiamondsNum(BigDecimal hadDiamondsNum) {
		this.hadDiamondsNum = hadDiamondsNum;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
