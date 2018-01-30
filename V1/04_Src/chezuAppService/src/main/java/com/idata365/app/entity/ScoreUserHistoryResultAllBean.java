package com.idata365.app.entity;

import java.io.Serializable;
import java.util.List;

public class ScoreUserHistoryResultAllBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5265680782921138924L;

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
