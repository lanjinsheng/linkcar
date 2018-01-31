package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class ImNotify implements Serializable {
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String leaderName;
	private Long leaderId;
	private String notifyMsg;
	private Date createTime;
	private Integer inUse;
	private Long familyId;
	private String familyName;
	private String leaderPic;
	
	
	
	public String getLeaderPic() {
		return leaderPic;
	}
	public void setLeaderPic(String leaderPic) {
		this.leaderPic = leaderPic;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public Long getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}
	public String getNotifyMsg() {
		return notifyMsg;
	}
	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getInUse() {
		return inUse;
	}
	public void setInUse(Integer inUse) {
		this.inUse = inUse;
	}
	
	
}
