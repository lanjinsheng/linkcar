package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.idata365.app.enums.PowerEnum;


/**
 * 
    * @ClassName: TaskAwardInfoPush
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年2月2日
    *
 */
public class TaskPowerLogs implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
    private Long userId;
    private Date taskDealTime;
    private String createTime;
    private PowerEnum taskType;
    private String jsonValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getTaskDealTime() {
		return taskDealTime;
	}
	public void setTaskDealTime(Date taskDealTime) {
		this.taskDealTime = taskDealTime;
	}
	 
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public PowerEnum getTaskType() {
		return taskType;
	}
	public void setTaskType(PowerEnum taskType) {
		this.taskType = taskType;
	}
   
 
}
