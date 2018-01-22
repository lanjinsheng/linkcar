package com.idata365.entity;

import java.io.Serializable;

public class FamilyDriveDayStat  implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long familyId;
	private String daystamp;
	private Integer useHoldNum;
	private Integer orderNo;
	private Double score;
	private Double scoreComm;
	private Double scoreAdd;
	private Integer speedTimes;
	private Integer  speedPenalTimes;
	private String speedTimesUpdateTime;
	private Integer brakeTimes;
	private Integer brakePenalTimes;
	private String  brakeTimesUpdateTime;
	private Integer turnTimes;
	private Integer turnPenalTimes;
	private String turnTimesUpdateTime;
	private Integer overspeedTimes;
	private Integer overspeedPenalTimes;
	private String overspeedTimesUpdateTime;
	private Integer nightDriveTimes;
	private Integer nightDrivePenalTimes;
	private String nightDriveTimesUpdateTime;
	private Integer tiredDriveTimes;
	private Integer tiredDrivePenalTimes;
	private String tiredDriveTimesUpdateTime;
	private Integer illegalStopTimes;
	private Integer illegalStopPenalTimes;
	private String illegalStopUpdateTime;
	private Integer usePhoneTimes;
	private Double maxspeed;
	private Double familyNumFactor;
	private Double roleFactor;
	private Double memberFactor;
	private Double familyLevelFactor;
	private Double mileage;
	private Double time;
	
	private String month;
	private String startDay;
	private String endDay;
	
	
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Integer getUseHoldNum() {
		return useHoldNum;
	}
	public void setUseHoldNum(Integer useHoldNum) {
		this.useHoldNum = useHoldNum;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getScoreComm() {
		return scoreComm;
	}
	public void setScoreComm(Double scoreComm) {
		this.scoreComm = scoreComm;
	}
	public Double getScoreAdd() {
		return scoreAdd;
	}
	public void setScoreAdd(Double scoreAdd) {
		this.scoreAdd = scoreAdd;
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
	public String getSpeedTimesUpdateTime() {
		return speedTimesUpdateTime;
	}
	public void setSpeedTimesUpdateTime(String speedTimesUpdateTime) {
		this.speedTimesUpdateTime = speedTimesUpdateTime;
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
	public String getTurnTimesUpdateTime() {
		return turnTimesUpdateTime;
	}
	public void setTurnTimesUpdateTime(String turnTimesUpdateTime) {
		this.turnTimesUpdateTime = turnTimesUpdateTime;
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
	public String getOverspeedTimesUpdateTime() {
		return overspeedTimesUpdateTime;
	}
	public void setOverspeedTimesUpdateTime(String overspeedTimesUpdateTime) {
		this.overspeedTimesUpdateTime = overspeedTimesUpdateTime;
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
	public String getTiredDriveTimesUpdateTime() {
		return tiredDriveTimesUpdateTime;
	}
	public void setTiredDriveTimesUpdateTime(String tiredDriveTimesUpdateTime) {
		this.tiredDriveTimesUpdateTime = tiredDriveTimesUpdateTime;
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
	public Integer getUsePhoneTimes() {
		return usePhoneTimes;
	}
	public void setUsePhoneTimes(Integer usePhoneTimes) {
		this.usePhoneTimes = usePhoneTimes;
	}
	public Double getMaxspeed() {
		return maxspeed;
	}
	public void setMaxspeed(Double maxspeed) {
		this.maxspeed = maxspeed;
	}
	public Double getFamilyNumFactor() {
		return familyNumFactor;
	}
	public void setFamilyNumFactor(Double familyNumFactor) {
		this.familyNumFactor = familyNumFactor;
	}
	public Double getRoleFactor() {
		return roleFactor;
	}
	public void setRoleFactor(Double roleFactor) {
		this.roleFactor = roleFactor;
	}
	public Double getMemberFactor() {
		return memberFactor;
	}
	public void setMemberFactor(Double memberFactor) {
		this.memberFactor = memberFactor;
	}
	public Double getFamilyLevelFactor() {
		return familyLevelFactor;
	}
	public void setFamilyLevelFactor(Double familyLevelFactor) {
		this.familyLevelFactor = familyLevelFactor;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
	
	
	
	

}
