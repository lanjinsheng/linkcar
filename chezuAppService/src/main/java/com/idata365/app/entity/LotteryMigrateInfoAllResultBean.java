package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class LotteryMigrateInfoAllResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5208350661269655804L;

	private String start;
	
	private List<LotteryMigrateInfoMsgResultBean> givenLottery;

	public String getStart()
	{
		return start;
	}

	public void setStart(String start)
	{
		this.start = start;
	}

	public List<LotteryMigrateInfoMsgResultBean> getGivenLottery()
	{
		return givenLottery;
	}

	public void setGivenLottery(List<LotteryMigrateInfoMsgResultBean> givenLottery)
	{
		this.givenLottery = givenLottery;
	}
}
