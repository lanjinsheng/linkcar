package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserLookAdLogs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;

	private Long id;
	private Long userId;
	private Date createTime;
	private String daystamp;
	private String remark;
	private BigDecimal diamondNum;
	private Long powerNum;
	private int adSign;
	private Long adPassId;
	private int valid;
	private int type;

	public BigDecimal getDiamondNum() {
		return diamondNum;
	}

	public void setDiamondNum(BigDecimal diamondNum) {
		this.diamondNum = diamondNum;
	}

	public int getAdSign() {
		return adSign;
	}

	public void setAdSign(int adSign) {
		this.adSign = adSign;
	}

	public Long getAdPassId() {
		return adPassId;
	}

	public void setAdPassId(Long adPassId) {
		this.adPassId = adPassId;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDaystamp() {
		return daystamp;
	}

	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPowerNum() {
		return powerNum;
	}

	public void setPowerNum(Long powerNum) {
		this.powerNum = powerNum;
	}

	public int getValid() {
		return valid;
	}

	public void setValid(int valid) {
		this.valid = valid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}
