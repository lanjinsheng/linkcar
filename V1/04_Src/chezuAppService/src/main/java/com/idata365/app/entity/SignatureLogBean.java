package com.idata365.app.entity;

public class SignatureLogBean
{
	//用户id
	private int userId;
	
	//最后签到日期
	private String lastSigDay;
	
	//连续签到天数
	private int sigCount;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getLastSigDay()
	{
		return lastSigDay;
	}

	public void setLastSigDay(String lastSigDay)
	{
		this.lastSigDay = lastSigDay;
	}

	public int getSigCount()
	{
		return sigCount;
	}

	public void setSigCount(int sigCount)
	{
		this.sigCount = sigCount;
	}
}
