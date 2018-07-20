package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Date;

public class UserLookAdLogs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;

	private Long id;
	private Long userId;
	private Date createTime;
	private String daystamp;
	private String remark;
	private Long powerNum;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDaystamp() {
		return daystamp;
	}

	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPowerNum() {
		return powerNum;
	}

	public void setPowerNum(Long powerNum) {
		this.powerNum = powerNum;
	}

}
