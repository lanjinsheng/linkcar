package com.idata365.entity;

import java.io.Serializable;
import java.util.Date;

public class TaskGameEnd implements Serializable{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long familyScoreId;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
	private Date taskDealTime;
	private Integer dayTimes;
	private Double addRatio;
	
	
	public Double getAddRatio() {
		return addRatio;
	}
	public void setAddRatio(Double addRatio) {
		this.addRatio = addRatio;
	}
	public Integer getDayTimes() {
		return dayTimes;
	}
	public void setDayTimes(Integer dayTimes) {
		this.dayTimes = dayTimes;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFamilyScoreId() {
		return familyScoreId;
	}
	public void setFamilyScoreId(Long familyScoreId) {
		this.familyScoreId = familyScoreId;
	}
	public String getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}
	public Integer getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Integer getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}
	public Date getTaskDealTime() {
		return taskDealTime;
	}
	public void setTaskDealTime(Date taskDealTime) {
		this.taskDealTime = taskDealTime;
	}
	
	
	
}
