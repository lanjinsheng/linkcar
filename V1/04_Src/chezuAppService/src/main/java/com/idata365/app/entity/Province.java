package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class Province implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8350879249385404020L;

	//区域编码
	private String upareaid;
	
	//名称
	private String upareaname;
	
	private List<City> cities;

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

	public List<City> getCities()
	{
		return cities;
	}

	public void setCities(List<City> cities)
	{
		this.cities = cities;
	}
	
}
