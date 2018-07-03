package com.idata365.entity;

import java.io.Serializable;
import java.util.Date;


public class UserConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Integer userConfigValue;
	private Date updateTime;
	private Integer type;

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

	public Integer getUserConfigValue() {
		return userConfigValue;
	}

	public void setUserConfigValue(Integer userConfigValue) {
		this.userConfigValue = userConfigValue;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}