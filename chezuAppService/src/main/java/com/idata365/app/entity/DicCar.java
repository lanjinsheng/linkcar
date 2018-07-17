package com.idata365.app.entity;

import java.io.Serializable;

public class DicCar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;

	private Long id;
	private Integer carId;//车辆id
	private String carName;//车辆名称
	private Integer carSeat;//搭载乘客数量
	private String carUrl;//车辆图片
	private String carLevel;//车辆级别 ：S、A、B
	private String carPropDesc;//车辆属性描述
	private String carBasePropDesc;//基础属性描述
	private Long powerConvert1;//20公里内动力转换能力：如：12
	private Long powerConvert2;//20-80公里内动力转换能力：如：8
	private Long powerConvert3;//80公里以上动力转换能力：如：5
	private Integer clubScoreUpPercent;//俱乐部成绩加成百分比
	private Integer isCanUnlock;//解锁是否可引导 1:true 0:false
	private String unlockCondition;//解锁条件描述
	private String unlockIconDesc;//解锁按钮描述
	private String unlockActionLink;//解锁引导地址
	
	public Integer getIsCanUnlock() {
		return isCanUnlock;
	}

	public void setIsCanUnlock(Integer isCanUnlock) {
		this.isCanUnlock = isCanUnlock;
	}

	public String getUnlockCondition() {
		return unlockCondition;
	}

	public void setUnlockCondition(String unlockCondition) {
		this.unlockCondition = unlockCondition;
	}

	public String getUnlockIconDesc() {
		return unlockIconDesc;
	}

	public void setUnlockIconDesc(String unlockIconDesc) {
		this.unlockIconDesc = unlockIconDesc;
	}

	public String getUnlockActionLink() {
		return unlockActionLink;
	}

	public void setUnlockActionLink(String unlockActionLink) {
		this.unlockActionLink = unlockActionLink;
	}

	public Integer getClubScoreUpPercent() {
		return clubScoreUpPercent;
	}

	public void setClubScoreUpPercent(Integer clubScoreUpPercent) {
		this.clubScoreUpPercent = clubScoreUpPercent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public Integer getCarSeat() {
		return carSeat;
	}

	public void setCarSeat(Integer carSeat) {
		this.carSeat = carSeat;
	}

	public String getCarUrl() {
		return carUrl;
	}

	public void setCarUrl(String carUrl) {
		this.carUrl = carUrl;
	}

	public String getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(String carLevel) {
		this.carLevel = carLevel;
	}

	public String getCarPropDesc() {
		return carPropDesc;
	}

	public void setCarPropDesc(String carPropDesc) {
		this.carPropDesc = carPropDesc;
	}

	public String getCarBasePropDesc() {
		return carBasePropDesc;
	}

	public void setCarBasePropDesc(String carBasePropDesc) {
		this.carBasePropDesc = carBasePropDesc;
	}

	public Long getPowerConvert1() {
		return powerConvert1;
	}

	public void setPowerConvert1(Long powerConvert1) {
		this.powerConvert1 = powerConvert1;
	}

	public Long getPowerConvert2() {
		return powerConvert2;
	}

	public void setPowerConvert2(Long powerConvert2) {
		this.powerConvert2 = powerConvert2;
	}

	public Long getPowerConvert3() {
		return powerConvert3;
	}

	public void setPowerConvert3(Long powerConvert3) {
		this.powerConvert3 = powerConvert3;
	}

}
