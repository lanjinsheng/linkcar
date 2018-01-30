package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class City implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2849682680263767257L;

	//区域编码
	private String upareaid;
	
	//名称
	private String upareaname;
	
	//地区
	private List<Area> areas;

	public List<Area> getAreas()
	{
		return areas;
	}

	public void setAreas(List<Area> areas)
	{
		this.areas = areas;
	}

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
