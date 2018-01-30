package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreDetailUnitBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8694509735018962460L;

	private String factor;
	
	private String value;
	
	private String weight;
	
	private String score;

	public String getFactor()
	{
		return factor;
	}

	public void setFactor(String factor)
	{
		this.factor = factor;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getWeight()
	{
		return weight;
	}

	public void setWeight(String weight)
	{
		this.weight = weight;
	}

	public String getScore()
	{
		return score;
	}

	public void setScore(String score)
	{
		this.score = score;
	}
}
