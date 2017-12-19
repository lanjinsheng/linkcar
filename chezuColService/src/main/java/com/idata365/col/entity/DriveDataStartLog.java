package com.idata365.col.entity;

import java.io.Serializable;
import java.util.Date;

public class DriveDataStartLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long habitId;
	private Date createTime;
	private String startTime;
	private int invalid;
	private int sendBss;
	private String equipmentInfo;
	private String deviceToken;
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
	public Long getHabitId() {
		return habitId;
	}
	public void setHabitId(Long habitId) {
		this.habitId = habitId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getInvalid() {
		return invalid;
	}
	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}
	public int getSendBss() {
		return sendBss;
	}
	public void setSendBss(int sendBss) {
		this.sendBss = sendBss;
	}
	public String getEquipmentInfo() {
		return equipmentInfo;
	}
	public void setEquipmentInfo(String equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

}
