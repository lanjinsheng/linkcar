package com.idata365.app.entity.v2;

import java.io.Serializable;

public class MissionLogsResultBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 332022778514848708L;

	private int missionId;
	private String missionName;
	private int finishCount;
	private int targetCount;
	private int status;
	private String daystamp;
	private int actionStatus;
	private String missionEndTime;
	private String linkDesc;
	private String powerPrize;
	private String otherPrize;
	private String missionDesc;
	private String missionActionLink;

	public int getMissionId() {
		return missionId;
	}

	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public int getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDaystamp() {
		return daystamp;
	}

	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}

	public int getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(int actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getMissionEndTime() {
		return missionEndTime;
	}

	public void setMissionEndTime(String missionEndTime) {
		this.missionEndTime = missionEndTime;
	}



	public String getLinkDesc() {
		return linkDesc;
	}

	public void setLinkDesc(String linkDesc) {
		this.linkDesc = linkDesc;
	}

	public String getPowerPrize() {
		return powerPrize;
	}

	public void setPowerPrize(String powerPrize) {
		this.powerPrize = powerPrize;
	}

	public String getOtherPrize() {
		return otherPrize;
	}

	public void setOtherPrize(String otherPrize) {
		this.otherPrize = otherPrize;
	}

	public String getMissionDesc() {
		return missionDesc;
	}

	public void setMissionDesc(String missionDesc) {
		this.missionDesc = missionDesc;
	}

	public String getMissionActionLink() {
		return missionActionLink;
	}

	public void setMissionActionLink(String missionActionLink) {
		this.missionActionLink = missionActionLink;
	}

}
