package com.idata365.app.entity;

import java.io.Serializable;

public class ScoreByDayBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -665683411604031660L;

	private double score;
	
	private double speedingState;
	
	private double driveState;
	
	private double creditState;
	
	private double steadyState;
	
	private double mileageState;

	public double getScore()
	{
		return score;
	}

	public void setScore(double score)
	{
		this.score = score;
	}

	public double getSpeedingState()
	{
		return speedingState;
	}

	public void setSpeedingState(double speedingState)
	{
		this.speedingState = speedingState;
	}

	public double getDriveState()
	{
		return driveState;
	}

	public void setDriveState(double driveState)
	{
		this.driveState = driveState;
	}

	public double getCreditState()
	{
		return creditState;
	}

	public void setCreditState(double creditState)
	{
		this.creditState = creditState;
	}

	public double getSteadyState()
	{
		return steadyState;
	}

	public void setSteadyState(double steadyState)
	{
		this.steadyState = steadyState;
	}

	public double getMileageState()
	{
		return mileageState;
	}

	public void setMileageState(double mileageState)
	{
		this.mileageState = mileageState;
	}

}
