package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class DicUserMission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3249981262869758886L;
	private Integer id;
	private Integer missionId;
	private Integer missionType;
	private String powerPrize;
	private String missionCondition;
	private String missionName;
	private String otherPrize;
	private String remark;
	private Integer targetCount;
	private Date endTime;
	private Integer actionStatus;
	private String actionLink;

	public String getPowerPrize() {
		return powerPrize;
	}

	public void setPowerPrize(String powerPrize) {
		this.powerPrize = powerPrize;
	}

	public Integer getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(Integer targetCount) {
		this.targetCount = targetCount;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(Integer actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getActionLink() {
		return actionLink;
	}

	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}

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
