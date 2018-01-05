package com.idata365.entity;

import java.io.Serializable;

public class UserScoreDayStat implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private Long userFamilyScoreId;
	private Long id;
	private String daystamp;
	private Double driveState;
	private Double steadyState;
	private Double mileageState;
	private Double speedingState;
	private Double score;
	private Double mileage;
	private Double mileageProportion;
	private Double mileageScore;
	private Double time;
	private Double timeProportion;
	private Double timeScore;
	private Integer brakeTimes;
	private Integer brakePenalTimes;
	private Double brakeTimesProportion;
	private Double brakeTimesScore;
	private String brakeTimesUpdateTime;
	private Integer turnTimes;
	private Integer turnPenalTimes;
	private Double turnTimesProportion;
	private Double turnTimesScore;
	private String turnTimesUpdateTime;
	private Integer speedTimes;
	private Integer speedPenalTimes;
	private Double speedTimesProportion;
	private Double speedTimesScore;
	private String speedTimesUpdateTime;
	private Integer overspeedTimes;
	private Integer overspeedPenalTimes;
	private Double overspeedTimesProportion;
	private Double overspeedTimesScore;
	private String overspeedTimesUpdateTime;
	private  Double maxspeed;
	private Double maxspeedProportion;
	private Double maxspeedScore;
	private Double tiredDrive;
	private Integer tiredDriveTimes;
	private Integer tiredDrivePenalTimes;
	private Double tiredDriveProportion;
	private Double tiredDriveScore;
	private String tiredDriveTimesUpdateTime;
	private Integer phoneTimes;
	private Double phoneTimesProportion;
	private Double phoneTimesScore;
	private Integer nightDriveTimes;
	private Integer nightDrivePenalTimes;
	private String nightDriveTimesUpdateTime;
	private Integer illegalStopTimes;
	private Integer illegalStopPenalTimes;
	private String illegalStopUpdateTime;
	private String weather;
	private Double weatherProportion;
	private Double weatherScore;
	private Integer walk;
	private Double walkProportion;
	private Double walkScore;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserFamilyScoreId() {
		return userFamilyScoreId;
	}
	public void setUserFamilyScoreId(Long userFamilyScoreId) {
		this.userFamilyScoreId = userFamilyScoreId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Double getDriveState() {
		return driveState;
	}
	public void setDriveState(Double driveState) {
		this.driveState = driveState;
	}
	public Double getSteadyState() {
		return steadyState;
	}
	public void setSteadyState(Double steadyState) {
		this.steadyState = steadyState;
	}
	public Double getMileageState() {
		return mileageState;
	}
	public void setMileageState(Double mileageState) {
		this.mileageState = mileageState;
	}
	public Double getSpeedingState() {
		return speedingState;
	}
	public void setSpeedingState(Double speedingState) {
		this.speedingState = speedingState;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public Double getMileageProportion() {
		return mileageProportion;
	}
	public void setMileageProportion(Double mileageProportion) {
		this.mileageProportion = mileageProportion;
	}
	public Double getMileageScore() {
		return mileageScore;
	}
	public void setMileageScore(Double mileageScore) {
		this.mileageScore = mileageScore;
	}
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
	public Double getTimeProportion() {
		return timeProportion;
	}
	public void setTimeProportion(Double timeProportion) {
		this.timeProportion = timeProportion;
	}
	public Double getTimeScore() {
		return timeScore;
	}
	public void setTimeScore(Double timeScore) {
		this.timeScore = timeScore;
	}
	public Integer getBrakeTimes() {
		return brakeTimes;
	}
	public void setBrakeTimes(Integer brakeTimes) {
		this.brakeTimes = brakeTimes;
	}
	public Integer getBrakePenalTimes() {
		return brakePenalTimes;
	}
	public void setBrakePenalTimes(Integer brakePenalTimes) {
		this.brakePenalTimes = brakePenalTimes;
	}
	public Double getBrakeTimesProportion() {
		return brakeTimesProportion;
	}
	public void setBrakeTimesProportion(Double brakeTimesProportion) {
		this.brakeTimesProportion = brakeTimesProportion;
	}
	public Double getBrakeTimesScore() {
		return brakeTimesScore;
	}
	public void setBrakeTimesScore(Double brakeTimesScore) {
		this.brakeTimesScore = brakeTimesScore;
	}
	public String getBrakeTimesUpdateTime() {
		return brakeTimesUpdateTime;
	}
	public void setBrakeTimesUpdateTime(String brakeTimesUpdateTime) {
		this.brakeTimesUpdateTime = brakeTimesUpdateTime;
	}
	public Integer getTurnTimes() {
		return turnTimes;
	}
	public void setTurnTimes(Integer turnTimes) {
		this.turnTimes = turnTimes;
	}
	public Integer getTurnPenalTimes() {
		return turnPenalTimes;
	}
	public void setTurnPenalTimes(Integer turnPenalTimes) {
		this.turnPenalTimes = turnPenalTimes;
	}
	public Double getTurnTimesProportion() {
		return turnTimesProportion;
	}
	public void setTurnTimesProportion(Double turnTimesProportion) {
		this.turnTimesProportion = turnTimesProportion;
	}
	public Double getTurnTimesScore() {
		return turnTimesScore;
	}
	public void setTurnTimesScore(Double turnTimesScore) {
		this.turnTimesScore = turnTimesScore;
	}
	public String getTurnTimesUpdateTime() {
		return turnTimesUpdateTime;
	}
	public void setTurnTimesUpdateTime(String turnTimesUpdateTime) {
		this.turnTimesUpdateTime = turnTimesUpdateTime;
	}
	public Integer getSpeedTimes() {
		return speedTimes;
	}
	public void setSpeedTimes(Integer speedTimes) {
		this.speedTimes = speedTimes;
	}
	public Integer getSpeedPenalTimes() {
		return speedPenalTimes;
	}
	public void setSpeedPenalTimes(Integer speedPenalTimes) {
		this.speedPenalTimes = speedPenalTimes;
	}
	public Double getSpeedTimesProportion() {
		return speedTimesProportion;
	}
	public void setSpeedTimesProportion(Double speedTimesProportion) {
		this.speedTimesProportion = speedTimesProportion;
	}
	public Double getSpeedTimesScore() {
		return speedTimesScore;
	}
	public void setSpeedTimesScore(Double speedTimesScore) {
		this.speedTimesScore = speedTimesScore;
	}
	public String getSpeedTimesUpdateTime() {
		return speedTimesUpdateTime;
	}
	public void setSpeedTimesUpdateTime(String speedTimesUpdateTime) {
		this.speedTimesUpdateTime = speedTimesUpdateTime;
	}
	public Integer getOverspeedTimes() {
		return overspeedTimes;
	}
	public void setOverspeedTimes(Integer overspeedTimes) {
		this.overspeedTimes = overspeedTimes;
	}
	public Integer getOverspeedPenalTimes() {
		return overspeedPenalTimes;
	}
	public void setOverspeedPenalTimes(Integer overspeedPenalTimes) {
		this.overspeedPenalTimes = overspeedPenalTimes;
	}
	public Double getOverspeedTimesProportion() {
		return overspeedTimesProportion;
	}
	public void setOverspeedTimesProportion(Double overspeedTimesProportion) {
		this.overspeedTimesProportion = overspeedTimesProportion;
	}
	public Double getOverspeedTimesScore() {
		return overspeedTimesScore;
	}
	public void setOverspeedTimesScore(Double overspeedTimesScore) {
		this.overspeedTimesScore = overspeedTimesScore;
	}
	public String getOverspeedTimesUpdateTime() {
		return overspeedTimesUpdateTime;
	}
	public void setOverspeedTimesUpdateTime(String overspeedTimesUpdateTime) {
		this.overspeedTimesUpdateTime = overspeedTimesUpdateTime;
	}
	public Double getMaxspeed() {
		return maxspeed;
	}
	public void setMaxspeed(Double maxspeed) {
		this.maxspeed = maxspeed;
	}
	public Double getMaxspeedProportion() {
		return maxspeedProportion;
	}
	public void setMaxspeedProportion(Double maxspeedProportion) {
		this.maxspeedProportion = maxspeedProportion;
	}
	public Double getMaxspeedScore() {
		return maxspeedScore;
	}
	public void setMaxspeedScore(Double maxspeedScore) {
		this.maxspeedScore = maxspeedScore;
	}
	public Double getTiredDrive() {
		return tiredDrive;
	}
	public void setTiredDrive(Double tiredDrive) {
		this.tiredDrive = tiredDrive;
	}
	public Integer getTiredDriveTimes() {
		return tiredDriveTimes;
	}
	public void setTiredDriveTimes(Integer tiredDriveTimes) {
		this.tiredDriveTimes = tiredDriveTimes;
	}
	public Integer getTiredDrivePenalTimes() {
		return tiredDrivePenalTimes;
	}
	public void setTiredDrivePenalTimes(Integer tiredDrivePenalTimes) {
		this.tiredDrivePenalTimes = tiredDrivePenalTimes;
	}
	public Double getTiredDriveProportion() {
		return tiredDriveProportion;
	}
	public void setTiredDriveProportion(Double tiredDriveProportion) {
		this.tiredDriveProportion = tiredDriveProportion;
	}
	public Double getTiredDriveScore() {
		return tiredDriveScore;
	}
	public void setTiredDriveScore(Double tiredDriveScore) {
		this.tiredDriveScore = tiredDriveScore;
	}
	public String getTiredDriveTimesUpdateTime() {
		return tiredDriveTimesUpdateTime;
	}
	public void setTiredDriveTimesUpdateTime(String tiredDriveTimesUpdateTime) {
		this.tiredDriveTimesUpdateTime = tiredDriveTimesUpdateTime;
	}
	public Integer getPhoneTimes() {
		return phoneTimes;
	}
	public void setPhoneTimes(Integer phoneTimes) {
		this.phoneTimes = phoneTimes;
	}
	public Double getPhoneTimesProportion() {
		return phoneTimesProportion;
	}
	public void setPhoneTimesProportion(Double phoneTimesProportion) {
		this.phoneTimesProportion = phoneTimesProportion;
	}
	public Double getPhoneTimesScore() {
		return phoneTimesScore;
	}
	public void setPhoneTimesScore(Double phoneTimesScore) {
		this.phoneTimesScore = phoneTimesScore;
	}
	public Integer getNightDriveTimes() {
		return nightDriveTimes;
	}
	public void setNightDriveTimes(Integer nightDriveTimes) {
		this.nightDriveTimes = nightDriveTimes;
	}
	public Integer getNightDrivePenalTimes() {
		return nightDrivePenalTimes;
	}
	public void setNightDrivePenalTimes(Integer nightDrivePenalTimes) {
		this.nightDrivePenalTimes = nightDrivePenalTimes;
	}
	public String getNightDriveTimesUpdateTime() {
		return nightDriveTimesUpdateTime;
	}
	public void setNightDriveTimesUpdateTime(String nightDriveTimesUpdateTime) {
		this.nightDriveTimesUpdateTime = nightDriveTimesUpdateTime;
	}
	public Integer getIllegalStopTimes() {
		return illegalStopTimes;
	}
	public void setIllegalStopTimes(Integer illegalStopTimes) {
		this.illegalStopTimes = illegalStopTimes;
	}
	public Integer getIllegalStopPenalTimes() {
		return illegalStopPenalTimes;
	}
	public void setIllegalStopPenalTimes(Integer illegalStopPenalTimes) {
		this.illegalStopPenalTimes = illegalStopPenalTimes;
	}
	public String getIllegalStopUpdateTime() {
		return illegalStopUpdateTime;
	}
	public void setIllegalStopUpdateTime(String illegalStopUpdateTime) {
		this.illegalStopUpdateTime = illegalStopUpdateTime;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public Double getWeatherProportion() {
		return weatherProportion;
	}
	public void setWeatherProportion(Double weatherProportion) {
		this.weatherProportion = weatherProportion;
	}
	public Double getWeatherScore() {
		return weatherScore;
	}
	public void setWeatherScore(Double weatherScore) {
		this.weatherScore = weatherScore;
	}
	public Integer getWalk() {
		return walk;
	}
	public void setWalk(Integer walk) {
		this.walk = walk;
	}
	public Double getWalkProportion() {
		return walkProportion;
	}
	public void setWalkProportion(Double walkProportion) {
		this.walkProportion = walkProportion;
	}
	public Double getWalkScore() {
		return walkScore;
	}
	public void setWalkScore(Double walkScore) {
		this.walkScore = walkScore;
	}
	
	
	

}
