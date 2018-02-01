package com.idata365.app.entity;

import java.io.Serializable;

public class UserFamilyRoleLogBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -747611848445848781L;

	private long id;
	
	private int role;
	
	private String startTime;
	
	private String endTime;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
}
