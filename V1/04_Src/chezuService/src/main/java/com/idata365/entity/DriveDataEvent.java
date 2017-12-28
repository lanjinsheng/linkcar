package com.idata365.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DriveDataEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long driveDataMainId;
	private int alarmType;
	private BigDecimal alarmValue;
	private BigDecimal lat;
	private BigDecimal lng;
	private String startTime;
	private String endTime;
	private BigDecimal maxspeed;
	
	public BigDecimal getMaxspeed() {
		return maxspeed;
	}
	public void setMaxspeed(BigDecimal maxspeed) {
		this.maxspeed = maxspeed;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDriveDataMainId() {
		return driveDataMainId;
	}
	public void setDriveDataMainId(Long driveDataMainId) {
		this.driveDataMainId = driveDataMainId;
	}
	public int getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}
	public BigDecimal getAlarmValue() {
		return alarmValue;
	}
	public void setAlarmValue(BigDecimal alarmValue) {
		this.alarmValue = alarmValue;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	

	 
}
