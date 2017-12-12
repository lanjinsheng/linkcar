package com.idata365.app.entity;

import java.io.Serializable;


public class LicenseDriver implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String userName;
	private String gender;
	private String nation;
	private String driveCardType;
	private String birthday;
	private String virginDay;
	private String validDay;
	private int  validYears;
	private String frontImgUrl;
	private String backImgUrl;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getDriveCardType() {
		return driveCardType;
	}
	public void setDriveCardType(String driveCardType) {
		this.driveCardType = driveCardType;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getVirginDay() {
		return virginDay;
	}
	public void setVirginDay(String virginDay) {
		this.virginDay = virginDay;
	}
	public String getValidDay() {
		return validDay;
	}
	public void setValidDay(String validDay) {
		this.validDay = validDay;
	}
	public int getValidYears() {
		return validYears;
	}
	public void setValidYears(int validYears) {
		this.validYears = validYears;
	}
	public String getFrontImgUrl() {
		return frontImgUrl;
	}
	public void setFrontImgUrl(String frontImgUrl) {
		this.frontImgUrl = frontImgUrl;
	}
	public String getBackImgUrl() {
		return backImgUrl;
	}
	public void setBackImgUrl(String backImgUrl) {
		this.backImgUrl = backImgUrl;
	}
	
	
	
}