package com.idata365.app.entity;

import java.io.Serializable;

public class SimulationScoreResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -21977463776097182L;

	private String roleId;
	
	private String score;

	public String getRoleId()
	{
		return roleId;
	}

	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
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
