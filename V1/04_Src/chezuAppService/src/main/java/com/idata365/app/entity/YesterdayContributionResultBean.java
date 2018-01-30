package com.idata365.app.entity;

import java.io.Serializable;

public class YesterdayContributionResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9155611839127384043L;

	private String userId;
	
	private String name;
	
	private String imgUrl;
	
	private String contribution;
	
	private String role;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
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

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public String getContribution()
	{
		return contribution;
	}

	public void setContribution(String contribution)
	{
		this.contribution = contribution;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

}
