package com.idata365.app.entity;

import java.io.Serializable;

public class SwitchRoleResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -493835719947334840L;

	private String tryFlag;
	
	private String msg;

	public String getTryFlag()
	{
		return tryFlag;
	}

	public void setTryFlag(String tryFlag)
	{
		this.tryFlag = tryFlag;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
}
