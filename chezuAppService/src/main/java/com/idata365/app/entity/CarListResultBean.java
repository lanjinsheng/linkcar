package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Map;

public class CarListResultBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;

	private String carId;// 车辆id
	private String carName;// 车辆名称
	private String carSeat;// 搭载乘客数量
	private String carUrl;// 车辆图片
	private String carLevel;// 车辆级别 ：S、A、B
	private String carPropDesc;// 车辆属性描述
	private String carBasePropDesc;// 基础属性描述
	private String powerConvert1;// 20公里内动力转换能力：如：12
	private String powerConvert2;// 20-80公里内动力转换能力：如：8
	private String powerConvert3;// 80公里以上动力转换能力：如：5
	private String powerConvertDesc;//动力转换描述
	private String isCanUnlock;// 解锁是否可引导 1:true 0:false
	private String unlockCondition;// 解锁条件描述
	private String unlockIconDesc;// 解锁按钮描述
	private String unlockActionLink;// 解锁引导地址
	private String isUnlock;// 是否解锁
	private String isDriving;// 是否驾驶
	private Map<String, String> powerUpInfo;
	
	public Map<String, String> getPowerUpInfo() {
		return powerUpInfo;
	}
	public void setPowerUpInfo(Map<String, String> powerUpInfo) {
		this.powerUpInfo = powerUpInfo;
	}
	public String getIsDriving() {
		return isDriving;
	}
	public void setIsDriving(String isDriving) {
		this.isDriving = isDriving;
	}
	public String getPowerConvertDesc() {
		return powerConvertDesc;
	}
	public void setPowerConvertDesc(String powerConvertDesc) {
		this.powerConvertDesc = powerConvertDesc;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getCarSeat() {
		return carSeat;
	}
	public void setCarSeat(String carSeat) {
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
	public String getPowerConvert1() {
		return powerConvert1;
	}
	public void setPowerConvert1(String powerConvert1) {
		this.powerConvert1 = powerConvert1;
	}
	public String getPowerConvert2() {
		return powerConvert2;
	}
	public void setPowerConvert2(String powerConvert2) {
		this.powerConvert2 = powerConvert2;
	}
	public String getPowerConvert3() {
		return powerConvert3;
	}
	public void setPowerConvert3(String powerConvert3) {
		this.powerConvert3 = powerConvert3;
	}
	public String getIsCanUnlock() {
		return isCanUnlock;
	}
	public void setIsCanUnlock(String isCanUnlock) {
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
	public String getIsUnlock() {
		return isUnlock;
	}
	public void setIsUnlock(String isUnlock) {
		this.isUnlock = isUnlock;
	}

}
