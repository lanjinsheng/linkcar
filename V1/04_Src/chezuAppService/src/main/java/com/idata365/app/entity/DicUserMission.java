package com.idata365.app.entity;

import java.io.Serializable;

public class DicUserMission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3249981262869758886L;
	private Integer id;
	private Integer missionId;
	private Integer missionType;
	private Integer powerPrize;
	private String missionCondition;
	private String missionName;
	private String otherPrize;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMissionId() {
		return missionId;
	}
	public void setMissionId(Integer missionId) {
		this.missionId = missionId;
	}
	public Integer getMissionType() {
		return missionType;
	}
	public void setMissionType(Integer missionType) {
		this.missionType = missionType;
	}
	public Integer getPowerPrize() {
		return powerPrize;
	}
	public void setPowerPrize(Integer powerPrize) {
		this.powerPrize = powerPrize;
	}
	public String getMissionCondition() {
		return missionCondition;
	}
	public void setMissionCondition(String missionCondition) {
		this.missionCondition = missionCondition;
	}
	public String getMissionName() {
		return missionName;
	}
	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}
	public String getOtherPrize() {
		return otherPrize;
	}
	public void setOtherPrize(String otherPrize) {
		this.otherPrize = otherPrize;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}
