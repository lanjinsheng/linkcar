package com.idata365.app.entity;

public class SignatureDayLogBean
{
	private long id;
	
	//用户id
	private long userId;
		
	//签到时间戳，格式：yyyyMMdd
	private String sigTimestamp;
	
	//签到月份，格式：yyyyMM
	private String month;
	
	private int count;
	
	private Integer num;
	
	//抽奖状态
	private String awardStatus;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getUserId()
	{
		return userId;
	}

	public void setUserId(long userId)
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

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public Integer getNum()
	{
		return num;
	}

	public void setNum(Integer num)
	{
		this.num = num;
	}

	public String getAwardStatus()
	{
		return awardStatus;
	}

	public void setAwardStatus(String awardStatus)
	{
		this.awardStatus = awardStatus;
	}
	
}
