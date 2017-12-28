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
	
	private int speedUpTimes=0;
	private int brakeTimes=0;
	private int turnTimes=0;
	private int speed120To129Times=0;
	private int speed130To139Times=0;
	private int speed140To149Times=0;
	private int speed150To159Times=0;
	private int speed160UpTimes=0;
	
	
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
	public int getSpeedUpTimes() {
		return speedUpTimes;
	}
	public void setSpeedUpTimes(int speedUpTimes) {
		this.speedUpTimes = speedUpTimes;
	}
	public int getBrakeTimes() {
		return brakeTimes;
	}
	public void setBrakeTimes(int brakeTimes) {
		this.brakeTimes = brakeTimes;
	}
	public int getTurnTimes() {
		return turnTimes;
	}
	public void setTurnTimes(int turnTimes) {
		this.turnTimes = turnTimes;
	}
	public int getSpeed120To129Times() {
		return speed120To129Times;
	}
	public void setSpeed120To129Times(int speed120To129Times) {
		this.speed120To129Times = speed120To129Times;
	}
	public int getSpeed130To139Times() {
		return speed130To139Times;
	}
	public void setSpeed130To139Times(int speed130To139Times) {
		this.speed130To139Times = speed130To139Times;
	}
	public int getSpeed140To149Times() {
		return speed140To149Times;
	}
	public void setSpeed140To149Times(int speed140To149Times) {
		this.speed140To149Times = speed140To149Times;
	}
	public int getSpeed150To159Times() {
		return speed150To159Times;
	}
	public void setSpeed150To159Times(int speed150To159Times) {
		this.speed150To159Times = speed150To159Times;
	}
	public int getSpeed160UpTimes() {
		return speed160UpTimes;
	}
	public void setSpeed160UpTimes(int speed160UpTimes) {
		this.speed160UpTimes = speed160UpTimes;
	}
	
	
}
