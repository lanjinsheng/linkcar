package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.idata365.app.enums.TaskGenericEnum;
/**
 * 
    * @ClassName: TaskGeneric
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年5月4日
    *
 */
public class TaskGeneric  implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = -9163330896225740999L;
	
	private Long id;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
	private Long taskDealTime;
	private Date createTime;
	private String jsonValue;
	private TaskGenericEnum taskType;
	private Integer priority;
	private String genericKey;
	
	public String getGenericKey() {
		return genericKey;
	}
	public void setGenericKey(String genericKey) {
		this.genericKey = genericKey;
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
	public Long getTaskDealTime() {
		return taskDealTime;
	}
	public void setTaskDealTime(Long taskDealTime) {
		this.taskDealTime = taskDealTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getJsonValue() {
		return jsonValue;
	}
	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	public TaskGenericEnum getTaskType() {
		return taskType;
	}
	public void setTaskType(TaskGenericEnum taskType) {
		this.taskType = taskType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	

}
