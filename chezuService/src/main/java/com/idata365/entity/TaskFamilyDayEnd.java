package com.idata365.entity;

import com.idata365.enums.AchieveEnum;

/**
 * 
    * @ClassName: TaskUserDayEnd
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月29日
    *
 */
public class TaskFamilyDayEnd
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long familyId;
	private Double taskValue;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
	private String  taskType;
    private String daystamp;
    
    
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getTaskValue() {
		return taskValue;
	}
	public void setTaskValue(Double taskValue) {
		this.taskValue = taskValue;
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
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	} 
    
    
}
