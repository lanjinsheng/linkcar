package com.idata365.app.entity;

import java.io.Serializable;

public class RoleCountBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3592937181483815536L;

	private int role;
	
	private int roleNum;

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public int getRoleNum()
	{
		return roleNum;
	}

	public void setRoleNum(int roleNum)
	{
		this.roleNum = roleNum;
	}
	
}
