package com.idata365.entity;

import java.io.Serializable;

public class CalDriveTask implements Serializable {
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long driveDataMainId;
	private Long taskFlag;
	private int calStatus;
	private Long userId;
	private Long 	habitId;
	private String calTime;
	private int calFailTimes;
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

	 
	public Long getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Long taskFlag) {
		this.taskFlag = taskFlag;
	}
	public int getCalStatus() {
		return calStatus;
	}
	public void setCalStatus(int calStatus) {
		this.calStatus = calStatus;
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
	public String getCalTime() {
		return calTime;
	}
	public void setCalTime(String calTime) {
		this.calTime = calTime;
	}
	public int getCalFailTimes() {
		return calFailTimes;
	}
	public void setCalFailTimes(int calFailTimes) {
		this.calFailTimes = calFailTimes;
	}
	
}
