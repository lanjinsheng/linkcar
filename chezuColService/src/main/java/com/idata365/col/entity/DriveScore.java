package com.idata365.col.entity;

import java.math.BigDecimal;

public class DriveScore {
	private Long id;
	private Long driveDataMainId;
	private BigDecimal tiredDrivingScore;
	private BigDecimal nightDrivingScore;
	private BigDecimal maxSpeedScore;
	private BigDecimal overSpeedScore;
	private BigDecimal brakeScore;
	private BigDecimal speedUpScore;
	private BigDecimal turnScore;
	private BigDecimal distanceScore;
	private BigDecimal phonePlayScore;
	private String taskFlag;
	private int calStatus;
	private Long userId;
	private Long 	habitId;
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
	public BigDecimal getTiredDrivingScore() {
		return tiredDrivingScore;
	}
	public void setTiredDrivingScore(BigDecimal tiredDrivingScore) {
		this.tiredDrivingScore = tiredDrivingScore;
	}
	public BigDecimal getNightDrivingScore() {
		return nightDrivingScore;
	}
	public void setNightDrivingScore(BigDecimal nightDrivingScore) {
		this.nightDrivingScore = nightDrivingScore;
	}
	public BigDecimal getMaxSpeedScore() {
		return maxSpeedScore;
	}
	public void setMaxSpeedScore(BigDecimal maxSpeedScore) {
		this.maxSpeedScore = maxSpeedScore;
	}
	public BigDecimal getOverSpeedScore() {
		return overSpeedScore;
	}
	public void setOverSpeedScore(BigDecimal overSpeedScore) {
		this.overSpeedScore = overSpeedScore;
	}
	public BigDecimal getBrakeScore() {
		return brakeScore;
	}
	public void setBrakeScore(BigDecimal brakeScore) {
		this.brakeScore = brakeScore;
	}
	public BigDecimal getSpeedUpScore() {
		return speedUpScore;
	}
	public void setSpeedUpScore(BigDecimal speedUpScore) {
		this.speedUpScore = speedUpScore;
	}
	public BigDecimal getTurnScore() {
		return turnScore;
	}
	public void setTurnScore(BigDecimal turnScore) {
		this.turnScore = turnScore;
	}
	public BigDecimal getDistanceScore() {
		return distanceScore;
	}
	public void setDistanceScore(BigDecimal distanceScore) {
		this.distanceScore = distanceScore;
	}
	public BigDecimal getPhonePlayScore() {
		return phonePlayScore;
	}
	public void setPhonePlayScore(BigDecimal phonePlayScore) {
		this.phonePlayScore = phonePlayScore;
	}
	public String getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(String taskFlag) {
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
	
}
