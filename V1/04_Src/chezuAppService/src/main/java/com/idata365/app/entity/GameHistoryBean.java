package com.idata365.app.entity;

import java.io.Serializable;

public class GameHistoryBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3405948906035329012L;

	private int orderNo;
	
	private String daystamp;

	public int getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(int orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getDaystamp()
	{
		return daystamp;
	}

	public void setDaystamp(String daystamp)
	{
		this.daystamp = daystamp;
	}
	
}
