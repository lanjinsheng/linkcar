package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class UsersAccountBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3696335894382191956L;

	private long id;
	
	private String nickName;
	
	private String phone;
	
	private String imgUrl;
	
	private Date createTime;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getNickName()
	{
		return nickName;
	}

	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}
