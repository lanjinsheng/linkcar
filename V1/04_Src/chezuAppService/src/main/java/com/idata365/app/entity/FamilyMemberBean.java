package com.idata365.app.entity;

public class FamilyMemberBean
{
	private long userId;
	
	private String name;
	
	private String phone;
	
	private double score;
	
	private String realRole;

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public String getRealRole()
	{
		return realRole;
	}

	public void setRealRole(String realRole)
	{
		this.realRole = realRole;
	}
	
}
