package com.idata365.app.entity;

import java.util.List;

public class LotteryMigrateInfoAllResultBean
{
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
