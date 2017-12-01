package com.idata365.col.entity;

import java.io.Serializable;
import java.util.Date;

public class UploadDataStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long habitId;
	private Date createTime;
	private Date sensorUploadTime;
//	private int  dataType;
	private int scanStatus;
	private int sensorUploadStatus;
	private String equipmentInfo;
	private int hadSensorData;
	private Long taskFlag;
	private Long createTimeSS;
	private int complete;
	
	private int inValid;
	private Long dealTimes;
	private Long scanTime;
	
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
	public Long getHabitId() {
		return habitId;
	}
	public void setHabitId(Long habitId) {
		this.habitId = habitId;
	}
 
	public int getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(int scanStatus) {
		this.scanStatus = scanStatus;
	}
	public String getEquipmentInfo() {
		return equipmentInfo;
	}
	public void setEquipmentInfo(String equipmentInfo) {
		this.equipmentInfo = equipmentInfo;
	}
	public int getHadSensorData() {
		return hadSensorData;
	}
	public void setHadSensorData(int hadSensorData) {
		this.hadSensorData = hadSensorData;
	}
	public int getSensorUploadStatus() {
		return sensorUploadStatus;
	}
	public void setSensorUploadStatus(int sensorUploadStatus) {
		this.sensorUploadStatus = sensorUploadStatus;
	}
	public Date getSensorUploadTime() {
		return sensorUploadTime;
	}
	public void setSensorUploadTime(Date sensorUploadTime) {
		this.sensorUploadTime = sensorUploadTime;
	}
	public Long getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Long taskFlag) {
		this.taskFlag = taskFlag;
	}
	public Long getCreateTimeSS() {
		return createTimeSS;
	}
	public void setCreateTimeSS(Long createTimeSS) {
		this.createTimeSS = createTimeSS;
	}
	public int getComplete() {
		return complete;
	}
	public void setComplete(int complete) {
		this.complete = complete;
	}
	public int getInValid() {
		return inValid;
	}
	public void setInValid(int inValid) {
		this.inValid = inValid;
	}
	public Long getDealTimes() {
		return dealTimes;
	}
	public void setDealTimes(Long dealTimes) {
		this.dealTimes = dealTimes;
	}
	public Long getScanTime() {
		return scanTime;
	}
	public void setScanTime(Long scanTime) {
		this.scanTime = scanTime;
	}
 
	
	
}
