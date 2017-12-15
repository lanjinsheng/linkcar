package com.idata365.app.entity;

import java.util.Date;

public class Message {
	private Long id;
	private Integer parentType;
	private Integer childType;
	private String title;
	private Date createTime;
	private String icon;
	private String picture;
	private String content;
	private Integer urlType;
	private Long toUserId;
	private Long fromUserId;
	private String bottomText;
	private Integer isRead;
	private Integer isPush;
	private Date readTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getParentType() {
		return parentType;
	}
	public void setParentType(Integer parentType) {
		this.parentType = parentType;
	}
	public Integer getChildType() {
		return childType;
	}
	public void setChildType(Integer childType) {
		this.childType = childType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUrlType() {
		return urlType;
	}
	public void setUrlType(Integer urlType) {
		this.urlType = urlType;
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public Long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getBottomText() {
		return bottomText;
	}
	public void setBottomText(String bottomText) {
		this.bottomText = bottomText;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getIsPush() {
		return isPush;
	}
	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
	
}
