package com.idata365.app.entity;

import java.io.Serializable;

public class FamilyRandResultBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8453543074642552563L;

	private String imgUrl;
	private String num;
	private String name;
	private String familyId;
	private String familyTypeValue;
	private String notifyMsg;

	public String getFamilyTypeValue() {
		return familyTypeValue;
	}

	public void setFamilyTypeValue(String familyTypeValue) {
		this.familyTypeValue = familyTypeValue;
	}

	public String getNotifyMsg() {
		return notifyMsg;
	}

	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}

	// 是否已经申请标记
	private String isAlreadyRecommend;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getIsAlreadyRecommend() {
		return isAlreadyRecommend;
	}

	public void setIsAlreadyRecommend(String isAlreadyRecommend) {
		this.isAlreadyRecommend = isAlreadyRecommend;
	}
}
