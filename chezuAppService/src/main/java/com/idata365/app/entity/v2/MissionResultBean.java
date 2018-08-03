package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Map;

public class MissionResultBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 332022778514848708L;

	private String missionId;
	private String missionProgress;
	private String missionActionStatus;
	private String missionEndTime;
	private String flag;
	private String missionDesc;
	private String missionName;
	private String missionReward;
	private String otherPrize;
	private String missionActionDesc;
	private String missionActionLink;
	private Map<String, Object> levelDic;

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getMissionProgress() {
		return missionProgress;
	}

	public void setMissionProgress(String missionProgress) {
		this.missionProgress = missionProgress;
	}

	public String getMissionActionStatus() {
		return missionActionStatus;
	}

	public void setMissionActionStatus(String missionActionStatus) {
		this.missionActionStatus = missionActionStatus;
	}

	public String getMissionEndTime() {
		return missionEndTime;
	}

	public void setMissionEndTime(String missionEndTime) {
		this.missionEndTime = missionEndTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMissionDesc() {
		return missionDesc;
	}

	public void setMissionDesc(String missionDesc) {
		this.missionDesc = missionDesc;
	}

	public String getMissionName() {
		return missionName;
	}

	public void setMissionName(String missionName) {
		this.missionName = missionName;
	}

	public String getMissionReward() {
		return missionReward;
	}

	public void setMissionReward(String missionReward) {
		this.missionReward = missionReward;
	}

	public String getOtherPrize() {
		return otherPrize;
	}

	public void setOtherPrize(String otherPrize) {
		this.otherPrize = otherPrize;
	}

	public String getMissionActionDesc() {
		return missionActionDesc;
	}

	public void setMissionActionDesc(String missionActionDesc) {
		this.missionActionDesc = missionActionDesc;
	}

	public String getMissionActionLink() {
		return missionActionLink;
	}

	public void setMissionActionLink(String missionActionLink) {
		this.missionActionLink = missionActionLink;
	}

	public Map<String, Object> getLevelDic() {
		return levelDic;
	}

	public void setLevelDic(Map<String, Object> levelDic) {
		this.levelDic = levelDic;
	}

}
