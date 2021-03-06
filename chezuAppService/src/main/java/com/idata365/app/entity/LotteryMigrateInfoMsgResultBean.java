package com.idata365.app.entity;

import java.io.Serializable;

public class LotteryMigrateInfoMsgResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7942321564490475284L;

	//赠送列表id
	private String id;
	
	//赠送用户id
	private String userId;
	
	//用户头像url
	private String imgUrl;
	
	//道具id
	private String awardId;
	
	//赠送数量
	private String awardCount;
	
	//赠送时间，格式：yyyyMMdd
	private String givenTime;
	
	//领取状态，"未领取"：UN_RECV；"领取"：RECV
	private String status;
	
	//赠送用户姓名
	private String name;
	
	private String day;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getAwardId()
	{
		return awardId;
	}

	public void setAwardId(String awardId)
	{
		this.awardId = awardId;
	}

	public String getAwardCount()
	{
		return awardCount;
	}

	public void setAwardCount(String awardCount)
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
