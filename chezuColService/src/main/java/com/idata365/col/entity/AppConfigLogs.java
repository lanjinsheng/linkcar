package com.idata365.col.entity;

import java.io.Serializable;
import java.util.Date;

public class AppConfigLogs implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Date createTime;
	private String isBgActive;
	private String isLocationStatus;
	private String isStepActive;
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
	public String getIsBgActive() {
		return isBgActive;
	}
	public void setIsBgActive(String isBgActive) {
		this.isBgActive = isBgActive;
	}
	public String getIsLocationStatus() {
		return isLocationStatus;
	}
	public void setIsLocationStatus(String isLocationStatus) {
		this.isLocationStatus = isLocationStatus;
	}
	public String getIsStepActive() {
		return isStepActive;
	}
	public void setIsStepActive(String isStepActive) {
		this.isStepActive = isStepActive;
	}
	

}
