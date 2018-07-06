package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class InteractPeccancy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long lawManId;
	private Long lawBreakerId;
	private Date createTime;
	private Date endTime;
	private String createTimeStr;
	private String endTimeStr;
	private Long endLong;
	private Integer payStatus;
	private Long payerId;
	private Date payTime;
	private Long carId;
	private Integer powerNum;
	
	
	
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public Integer getPowerNum() {
		return powerNum;
	}
	public void setPowerNum(Integer powerNum) {
		this.powerNum = powerNum;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLawManId() {
		return lawManId;
	}
	public void setLawManId(Long lawManId) {
		this.lawManId = lawManId;
	}
	public Long getLawBreakerId() {
		return lawBreakerId;
	}
	public void setLawBreakerId(Long lawBreakerId) {
		this.lawBreakerId = lawBreakerId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getEndLong() {
		return endLong;
	}
	public void setEndLong(Long endLong) {
		this.endLong = endLong;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Long getPayerId() {
		return payerId;
	}
	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	
}
