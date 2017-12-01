package com.idata365.col.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DriveDataMain implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long habitId;
	private Long taskFlag;
	private Date createTime;
	private String driveStartTime;
	private String driveEndTime;
	private Long driveTimes;
	private BigDecimal maxSpeed;
	private BigDecimal driveDistance;
	private BigDecimal avgSpeed;
	private int isPost;
	private int validStatus;
	private Date postTime;
	
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
 
	public String getDriveStartTime() {
		return driveStartTime;
	}
	public void setDriveStartTime(String driveStartTime) {
		this.driveStartTime = driveStartTime;
	}
	public String getDriveEndTime() {
		return driveEndTime;
	}
	public void setDriveEndTime(String driveEndTime) {
		this.driveEndTime = driveEndTime;
	}
	public Long getDriveTimes() {
		return driveTimes;
	}
	public void setDriveTimes(Long driveTimes) {
		this.driveTimes = driveTimes;
	}
	public BigDecimal getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(BigDecimal maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public int getIsPost() {
		return isPost;
	}
	public void setIsPost(int isPost) {
		this.isPost = isPost;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public BigDecimal getDriveDistance() {
		return driveDistance;
	}
	public void setDriveDistance(BigDecimal driveDistance) {
		this.driveDistance = driveDistance;
	}
	public BigDecimal getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(BigDecimal avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public int getValidStatus() {
		return validStatus;
	}
	public void setValidStatus(int validStatus) {
		this.validStatus = validStatus;
	}
	public Long getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Long taskFlag) {
		this.taskFlag = taskFlag;
	}
	
	
}
