package com.idata365.app.entity;

import java.io.Serializable;

public class Area implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7752776535028299165L;

	//区域编码
	private String upareaid;
	
	//名称
	private String upareaname;

	public String getUpareaid()
	{
		return upareaid;
	}

	public void setUpareaid(String upareaid)
	{
		this.upareaid = upareaid;
	}

	public String getUpareaname()
	{
		return upareaname;
	}

	public void setUpareaname(String upareaname)
	{
		this.upareaname = upareaname;
	}
	
}
