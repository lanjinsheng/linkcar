package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyMemberResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -674501718439785023L;

	private String name;
	
	private String imgUrl;
	
	private String score;
	
	private String percent;
	
	private String realRole;
	
	private String supposeRole;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getScore()
	{
		return score;
	}

	public void setScore(String score)
	{
		this.score = score;
	}

	public String getPercent()
	{
		return percent;
	}

	public void setPercent(String percent)
	{
		this.percent = percent;
	}

	public String getRealRole()
	{
		return realRole;
	}

	public void setRealRole(String realRole)
	{
		this.realRole = realRole;
	}

	public String getSupposeRole()
	{
		return supposeRole;
	}

	public void setSupposeRole(String supposeRole)
	{
		this.supposeRole = supposeRole;
	}
	
}
