package com.idata365.app.entity;

import java.util.List;

public class FamilyMemberAllResultBean
{
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
