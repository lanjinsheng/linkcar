package com.idata365.app.entity;

import java.io.Serializable;

/**
 * 
 * @className:com.idata365.app.entity.SystemVisionBean
 * @description:TODO
 * @date:2018年2月1日 下午2:49:06
 * @author:CaiFengYao
 */
public class SystemVisionBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int phoneType;
	// 1最新版本;2.强制升级;3.允许升级
	private int status;
	private String vision;
	private String url;

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getPhoneType()
	{
		return phoneType;
	}

	public void setPhoneType(int phoneType)
	{
		this.phoneType = phoneType;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getVision()
	{
		return vision;
	}

	public void setVision(String vision)
	{
		this.vision = vision;
	}

}
