package com.idata365.app.entity;

public class LotteryMigrateInfoMsgBean
{
	//赠送列表id
	private int id;
	
	//赠送用户id
	private long userId;
	
	//被赠送用户id
	private int toUserId;
	
	//道具id
	private int awardId;
	
	//赠送数量
	private int awardCount;
	
	//赠送时间，格式：yyyyMMddHHmmss
	private String givenTime;
	
	//领取状态，"未领取"：UN_RECV；"领取"：RECV
	private String status;
	
	//赠送用户姓名
	private String name;
	
	private String day;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public int getToUserId()
	{
		return toUserId;
	}

	public void setToUserId(int toUserId)
	{
		this.toUserId = toUserId;
	}

	public int getAwardId()
	{
		return awardId;
	}

	public void setAwardId(int awardId)
	{
		this.awardId = awardId;
	}

	public int getAwardCount()
	{
		return awardCount;
	}

	public void setAwardCount(int awardCount)
	{
		this.awardCount = awardCount;
	}

	public String getGivenTime()
	{
		return givenTime;
	}

	public void setGivenTime(String givenTime)
	{
		this.givenTime = givenTime;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}
	
}
