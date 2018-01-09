package com.idata365.entity;

import java.io.Serializable;

public class TaskFamilyPk implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer selfFamilyLevel;
	private Integer competitorFamilyLevel;
	private Long selfFamilyId;
	private Long competitorFamilyId;
	private String daystamp;
	
	private String taskFlag;
    private Integer taskStatus;
	private Integer failTimes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
 
	public Integer getSelfFamilyLevel() {
		return selfFamilyLevel;
	}
	public void setSelfFamilyLevel(Integer selfFamilyLevel) {
		this.selfFamilyLevel = selfFamilyLevel;
	}
	public Integer getCompetitorFamilyLevel() {
		return competitorFamilyLevel;
	}
	public void setCompetitorFamilyLevel(Integer competitorFamilyLevel) {
		this.competitorFamilyLevel = competitorFamilyLevel;
	}
	public Long getSelfFamilyId() {
		return selfFamilyId;
	}
	public void setSelfFamilyId(Long selfFamilyId) {
		this.selfFamilyId = selfFamilyId;
	}
	public Long getCompetitorFamilyId() {
		return competitorFamilyId;
	}
	public void setCompetitorFamilyId(Long competitorFamilyId) {
		this.competitorFamilyId = competitorFamilyId;
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
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Integer getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}


}