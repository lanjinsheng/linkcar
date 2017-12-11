package com.idata365.app.entity;

import java.util.List;

public class SignInResultBean
{
	private List<String> sigDays;
	
	private int sigCount;

	public List<String> getSigDays()
	{
		return sigDays;
	}

	public void setSigDays(List<String> sigDays)
	{
		this.sigDays = sigDays;
	}

	public int getSigCount()
	{
		return sigCount;
	}

	public void setSigCount(int sigCount)
	{
		this.sigCount = sigCount;
	}
}
