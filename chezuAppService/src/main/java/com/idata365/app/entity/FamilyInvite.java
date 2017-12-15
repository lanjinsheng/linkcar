package com.idata365.app.entity;

import java.util.Date;

public class FamilyInvite {
	private Long id;
	private Long familyId;
	private String memberPhone;
	private Long memberUserId;
	private int sendInviteMsg;
	private Date createTime;
	private Date sendTime;
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
