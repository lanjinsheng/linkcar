package com.idata365.app.entity;

public class ScoreFamilyInfoBean
{
	private String imgUrl;
	
	private long familyId;
	
	private int awardType;
	
	private int orderChange;
	
	private int orderNo;
	
	private double yesterdayScore;
	
	private String name;
	
	private double score;

	private long createUserId;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public int getAwardType()
	{
		return awardType;
	}

	public void setAwardType(int awardType)
	{
		this.awardType = awardType;
	}

	public int getOrderChange()
	{
		return orderChange;
	}

	public void setOrderChange(int orderChange)
	{
		this.orderChange = orderChange;
	}

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public double getYesterdayScore()
	{
		return yesterdayScore;
	}

	public void setYesterdayScore(double yesterdayScore)
	{
		this.yesterdayScore = yesterdayScore;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public long getCreateUserId()
	{
		return createUserId;
	}

	public void setCreateUserId(long createUserId)
	{
		this.createUserId = createUserId;
	}
}
