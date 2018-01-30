package com.idata365.app.entity;

import java.io.Serializable;

public class RoleCountResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6016835492767203740L;

	private String role;
	
	private String percent;

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getPercent()
	{
		return percent;
	}

	public void setPercent(String percent)
	{
		this.percent = percent;
	}
	
}
