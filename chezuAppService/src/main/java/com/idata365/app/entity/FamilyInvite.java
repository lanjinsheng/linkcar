package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class FamilyInvite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4642595705931335129L;
	private Long id;
	private Long familyId;
	private String memberPhone;
	private Long memberUserId;
	private int sendInviteMsg;
	private Date createTime;
	private Date sendTime;
	private String leaveWord;
	
	public String getLeaveWord() {
		return leaveWord;
	}
	public void setLeaveWord(String leaveWord) {
		this.leaveWord = leaveWord;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public Long getMemberUserId() {
		return memberUserId;
	}
	public void setMemberUserId(Long memberUserId) {
		this.memberUserId = memberUserId;
	}
	public int getSendInviteMsg() {
		return sendInviteMsg;
	}
	public void setSendInviteMsg(int sendInviteMsg) {
		this.sendInviteMsg = sendInviteMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	
	
}
