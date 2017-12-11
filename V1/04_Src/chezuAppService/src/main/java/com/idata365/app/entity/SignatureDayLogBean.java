package com.idata365.app.entity;

public class SignatureDayLogBean
{
	//用户id
	private int userId;
		
	//签到时间戳，格式：yyyyMMdd
	private String sigTimestamp;
	
	//签到月份，格式：yyyyMM
	private String month;

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getSigTimestamp()
	{
		return sigTimestamp;
	}

	public void setSigTimestamp(String sigTimestamp)
	{
		this.sigTimestamp = sigTimestamp;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}
	
}
