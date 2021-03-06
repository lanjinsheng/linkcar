package com.idata365.app.entity;

import java.io.Serializable;

public class UserFamilyRelationBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8536201562648567964L;

	private long userId;
	
	private long familyId;
	
	private int role;
	
	private String joinTime;

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public String getJoinTime()
	{
		return joinTime;
	}

	public void setJoinTime(String joinTime)
	{
		this.joinTime = joinTime;
	}
}
