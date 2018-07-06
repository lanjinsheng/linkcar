package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class InteractAirdrop implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7746428270083270693L;

	private Long id;
	private Long userId;
	private Integer faultNum;
	private Integer leftFaultNum;
	private Integer powerNum;
	private Integer leftPowerNum;
	private String daystamp;
	private Long lockTime;
	private Long locker;
	private String stealers;
	private Integer lockStatus;
	private Date updateTime;
	private String lockBatchId;
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
	public Integer getFaultNum() {
		return faultNum;
	}
	public void setFaultNum(Integer faultNum) {
		this.faultNum = faultNum;
	}
	public Integer getLeftFaultNum() {
		return leftFaultNum;
	}
	public void setLeftFaultNum(Integer leftFaultNum) {
		this.leftFaultNum = leftFaultNum;
	}
	public Integer getPowerNum() {
		return powerNum;
	}
	public void setPowerNum(Integer powerNum) {
		this.powerNum = powerNum;
	}
	public Integer getLeftPowerNum() {
		return leftPowerNum;
	}
	public void setLeftPowerNum(Integer leftPowerNum) {
		this.leftPowerNum = leftPowerNum;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Long getLockTime() {
		return lockTime;
	}
	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
	}
	public Long getLocker() {
		return locker;
	}
	public void setLocker(Long locker) {
		this.locker = locker;
	}
	public String getStealers() {
		return stealers;
	}
	public void setStealers(String stealers) {
		this.stealers = stealers;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLockBatchId() {
		return lockBatchId;
	}
	public void setLockBatchId(String lockBatchId) {
		this.lockBatchId = lockBatchId;
	}
	
	

}
