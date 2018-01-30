package com.idata365.app.entity;

import java.io.Serializable;

public class GameHistoryResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7394718852275244972L;

	private String orderNo;
	
	private String daystamp;

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
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
