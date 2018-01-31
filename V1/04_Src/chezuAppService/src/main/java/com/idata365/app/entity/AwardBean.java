package com.idata365.app.entity;

import java.io.Serializable;

public class AwardBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3953890057492841812L;

	private long id;
	
	//获奖消息
	private String headMsg;
	
	//昵称
	private String nickName;
	
	//年龄
	private int age;
	
	//职业
	private String job;
	
	//驾龄
	private String driveAge;
	
	//语录
	private String motto;
	
	//头像url
	private String imgUrl;
	
	//奖品
	private String award;
	
	//
	private String awardImg;
	
	//
	private String nextAwardTime;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getHeadMsg()
	{
		return headMsg;
	}

	public void setHeadMsg(String headMsg)
	{
		this.headMsg = headMsg;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getJob()
	{
		return job;
	}

	public void setJob(String job)
	{
		this.job = job;
	}

	public String getDriveAge()
	{
		return driveAge;
	}

	public void setDriveAge(String driveAge)
	{
		this.driveAge = driveAge;
	}

	public String getMotto()
	{
		return motto;
	}

	public void setMotto(String motto)
	{
		this.motto = motto;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getAward()
	{
		return award;
	}

	public void setAward(String award)
	{
		this.award = award;
	}

	public String getAwardImg()
	{
		return awardImg;
	}

	public void setAwardImg(String awardImg)
	{
		this.awardImg = awardImg;
	}

	public String getNextAwardTime()
	{
		return nextAwardTime;
	}

	public void setNextAwardTime(String nextAwardTime)
	{
		this.nextAwardTime = nextAwardTime;
	}
	
}
