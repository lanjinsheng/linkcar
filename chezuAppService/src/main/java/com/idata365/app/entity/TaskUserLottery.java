package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
    * @ClassName: TaskMessagePush
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年2月2日
    *
 */
public class TaskUserLottery implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
    private Long messageId;
    private Date taskDealTime;
    private Date createTime;
    private String daystamp;
    
    
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
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
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Date getTaskDealTime() {
		return taskDealTime;
	}
	public void setTaskDealTime(Date taskDealTime) {
		this.taskDealTime = taskDealTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
 
 
}
