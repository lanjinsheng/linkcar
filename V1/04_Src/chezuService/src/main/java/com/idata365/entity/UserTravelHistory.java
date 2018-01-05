package com.idata365.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserTravelHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Long habitId;
	private String startTime;
	private String endTime;
	
	String createTime;
	double mileage;
	int time;
	private int speedTimes=0;
	private int brakeTimes=0;
	private int turnTimes=0;
	private int overspeedTimes=0;
	double nightDrive;
	double tiredDrive;
	double maxspeed;
	
	int brakeTimesOffset;
	int speedTimesOffset;
	int turnTimesOffset;
	int overspeedTimesOffset;
	int nightDriveOffset;
	int tiredDriveOffset;
	int highSpeedTimesOffset;
	
	private int addDayStatFlag;
	private String taskFlag;
	private Date taskDealTime;
	private int failTimes;
	
	public int getUseShachepian() {
		return useShachepian;
	}
	public void setUseShachepian(int useShachepian) {
		this.useShachepian = useShachepian;
	}
	public int getUseHongniu() {
		return useHongniu;
	}
	public void setUseHongniu(int useHongniu) {
		this.useHongniu = useHongniu;
	}
	public int getUseYeshijing() {
		return useYeshijing;
	}
	public void setUseYeshijing(int useYeshijing) {
		this.useYeshijing = useYeshijing;
	}
	public int getUseFadongji() {
		return useFadongji;
	}
	public void setUseFadongji(int useFadongji) {
		this.useFadongji = useFadongji;
	}
	public int getUseCheluntai() {
		return useCheluntai;
	}
	public void setUseCheluntai(int useCheluntai) {
		this.useCheluntai = useCheluntai;
	}
	public int getUseZengyaqi() {
		return useZengyaqi;
	}
	public void setUseZengyaqi(int useZengyaqi) {
		this.useZengyaqi = useZengyaqi;
	}
	private int useShachepian;
	private int useHongniu;
	private int useYeshijing;
	private int useFadongji;
	private int useCheluntai;
	private int useZengyaqi;
	
	
	
	public int getAddDayStatFlag() {
		return addDayStatFlag;
	}
	public void setAddDayStatFlag(int addDayStatFlag) {
		this.addDayStatFlag = addDayStatFlag;
	}
	public String getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}
	public Date getTaskDealTime() {
		return taskDealTime;
	}
	public void setTaskDealTime(Date taskDealTime) {
		this.taskDealTime = taskDealTime;
	}
	public int getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(int failTimes) {
		this.failTimes = failTimes;
	}
	public int getBrakeTimesOffset() {
		return brakeTimesOffset;
	}
	public void setBrakeTimesOffset(int brakeTimesOffset) {
		this.brakeTimesOffset = brakeTimesOffset;
	}
	public int getSpeedTimesOffset() {
		return speedTimesOffset;
	}
	public void setSpeedTimesOffset(int speedTimesOffset) {
		this.speedTimesOffset = speedTimesOffset;
	}
	public int getTurnTimesOffset() {
		return turnTimesOffset;
	}
	public void setTurnTimesOffset(int turnTimesOffset) {
		this.turnTimesOffset = turnTimesOffset;
	}
	public int getOverspeedTimesOffset() {
		return overspeedTimesOffset;
	}
	public void setOverspeedTimesOffset(int overspeedTimesOffset) {
		this.overspeedTimesOffset = overspeedTimesOffset;
	}
	public int getNightDriveOffset() {
		return nightDriveOffset;
	}
	public void setNightDriveOffset(int nightDriveOffset) {
		this.nightDriveOffset = nightDriveOffset;
	}
	public int getTiredDriveOffset() {
		return tiredDriveOffset;
	}
	public void setTiredDriveOffset(int tiredDriveOffset) {
		this.tiredDriveOffset = tiredDriveOffset;
	}
	public int getHighSpeedTimesOffset() {
		return highSpeedTimesOffset;
	}
	public void setHighSpeedTimesOffset(int highSpeedTimesOffset) {
		this.highSpeedTimesOffset = highSpeedTimesOffset;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getHabitId() {
		return habitId;
	}
	public void setHabitId(Long habitId) {
		this.habitId = habitId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getSpeedTimes() {
		return speedTimes;
	}
	public void setSpeedTimes(int speedTimes) {
		this.speedTimes = speedTimes;
	}
	public int getBrakeTimes() {
		return brakeTimes;
	}
	public void setBrakeTimes(int brakeTimes) {
		this.brakeTimes = brakeTimes;
	}
	public int getTurnTimes() {
		return turnTimes;
	}
	public void setTurnTimes(int turnTimes) {
		this.turnTimes = turnTimes;
	}
	public int getOverspeedTimes() {
		return overspeedTimes;
	}
	public void setOverspeedTimes(int overspeedTimes) {
		this.overspeedTimes = overspeedTimes;
	}
	public double getNightDrive() {
		return nightDrive;
	}
	public void setNightDrive(double nightDrive) {
		this.nightDrive = nightDrive;
	}
	public double getTiredDrive() {
		return tiredDrive;
	}
	public void setTiredDrive(double tiredDrive) {
		this.tiredDrive = tiredDrive;
	}
	public double getMaxspeed() {
		return maxspeed;
	}
	public void setMaxspeed(double maxspeed) {
		this.maxspeed = maxspeed;
	}
	
	
}
