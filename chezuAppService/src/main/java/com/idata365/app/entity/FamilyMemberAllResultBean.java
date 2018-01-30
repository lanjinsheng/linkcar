package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class FamilyMemberAllResultBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3042426004369602193L;

	private String simulationScore;
	
	private List<FamilyMemberResultBean> scoreArr;

	public String getSimulationScore()
	{
		return simulationScore;
	}

	public void setSimulationScore(String simulationScore)
	{
		this.simulationScore = simulationScore;
	}

	public List<FamilyMemberResultBean> getScoreArr()
	{
		return scoreArr;
	}

	public void setScoreArr(List<FamilyMemberResultBean> scoreArr)
	{
		this.scoreArr = scoreArr;
	}
}
