package com.idata365.app.entity;

import java.util.List;

public class ScoreUserHistoryResultAllBean
{
	private String start;
	
	private List<ScoreUserHistoryResultBean> historyScores;

	public String getStart()
	{
		return start;
	}

	public void setStart(String start)
	{
		this.start = start;
	}

	public List<ScoreUserHistoryResultBean> getHistoryScores()
	{
		return historyScores;
	}

	public void setHistoryScores(List<ScoreUserHistoryResultBean> historyScores)
	{
		this.historyScores = historyScores;
	}
}
