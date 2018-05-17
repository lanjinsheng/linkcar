package com.idata365.entity;

import java.io.Serializable;

public class TaskFamilyMonthAvgOrder implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long familyId;
	private Integer familyType;
	private String taskFlag;
    private Integer taskStatus;
	private String month;
	private Integer failTimes;
	private Double avgScore;
	private Integer trophy;
	private int orderNo;
	private String startDay;
	private String endDay;
	
	
	public Integer getFamilyType() {
		return familyType;
	}
	public void setFamilyType(Integer familyType) {
		this.familyType = familyType;
	}
	public Integer getTrophy() {
		return trophy;
	}
	public void setTrophy(Integer trophy) {
		this.trophy = trophy;
	}
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public Double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(Double avgScore) {
		this.avgScore = avgScore;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
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
 
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}


}