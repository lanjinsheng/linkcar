package com.idata365.entity;

/**
 * 
 * @className:com.idata365.entity.TaskGiveUserAchieveBean
 * @description:TODO
 * @date:2018年1月23日 下午3:39:03
 * @author:CaiFengYao
 */
public class TaskGiveUserAchieveBean
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long achieveRecordId;
	private String taskFlag;
	private Integer taskStatus;
	private Integer failTimes;
	private String daystamp;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getAchieveRecordId()
	{
		return achieveRecordId;
	}

	public void setAchieveRecordId(Long achieveRecordId)
	{
		this.achieveRecordId = achieveRecordId;
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

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
