package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

public class ImMsg implements Serializable {
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long familyId;
	private String fromUserPic;
	private Long fromUserId;
	private String toUserPic;
	private Long toUserId;
	private Date createTime;
	private String msg;
	private Integer isRead;
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public String getFromUserPic() {
		return fromUserPic;
	}
	public void setFromUserPic(String fromUserPic) {
		this.fromUserPic = fromUserPic;
	}
	public Long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserPic() {
		return toUserPic;
	}
	public void setToUserPic(String toUserPic) {
		this.toUserPic = toUserPic;
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
	
	
}
