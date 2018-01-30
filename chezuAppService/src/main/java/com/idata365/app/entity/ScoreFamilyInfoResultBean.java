package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreFamilyInfoResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4417921631501039956L;

	private String imgUrl;
	
	private String familyId;
	
	private String awardType;
	
	private String orderChange;
	
	private String orderNo;
	
	private String yesterdayScore;
	
	private String name;
	
	private String score;

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(String familyId)
	{
		this.familyId = familyId;
	}

	public String getAwardType()
	{
		return awardType;
	}

	public void setAwardType(String awardType)
	{
		this.awardType = awardType;
	}

	public String getOrderChange()
	{
		return orderChange;
	}

	public void setOrderChange(String orderChange)
	{
		this.orderChange = orderChange;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getYesterdayScore()
	{
		return yesterdayScore;
	}

	public void setYesterdayScore(String yesterdayScore)
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

	public String getScore()
	{
		return score;
	}

	public void setScore(String score)
	{
		this.score = score;
	}
	
}
