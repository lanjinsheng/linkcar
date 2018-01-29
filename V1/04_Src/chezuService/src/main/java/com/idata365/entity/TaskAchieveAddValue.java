package com.idata365.entity;

import com.idata365.enums.AchieveEnum;

/**
 * 
 * @className:com.idata365.entity.TaskGiveUserAchieveBean
 * @description:TODO
 * @date:2018年1月23日 下午3:39:03
 * @author:CaiFengYao
 */
public class TaskAchieveAddValue
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long keyId;
	private Double addValue;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
	private AchieveEnum achieveType=AchieveEnum.AddBestDriverTimes;

	public AchieveEnum getAchieveType() {
		return achieveType;
	}

	public void setAchieveType(AchieveEnum achieveType) {
		this.achieveType = achieveType;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	 

	public String getTaskFlag()
	{
		return taskFlag;
	}

	public void setTaskFlag(String taskFlag)
	{
		this.taskFlag = taskFlag;
	}

	public Integer getTaskStatus()
	{
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus)
	{
		this.taskStatus = taskStatus;
	}

	public Integer getFailTimes()
	{
		return failTimes;
	}

	public void setFailTimes(Integer failTimes)
	{
		this.failTimes = failTimes;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Double getAddValue() {
		return addValue;
	}

	public void setAddValue(Double addValue) {
		this.addValue = addValue;
	}
 
}
