package com.idata365.col.entity;

import java.io.Serializable;
import java.util.Date;

public class DriveDataLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private String filePath;
	private Long habitId;
	private int isEnd;
	private int seq;
	private Date createTime;
	private String equipmentInfo;
	private int hadSensorData;
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	public int getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
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
	
}
