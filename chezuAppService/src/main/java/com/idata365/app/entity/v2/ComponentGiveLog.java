package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Date;

public class ComponentGiveLog implements Serializable{
	/**
	 * 
	 */
 
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long fromId;
	private Integer componentId;
	private Long fromComponentId;
	private Integer logType;
	private Date eventTime;
	private Long toUserId;
	private Integer giveStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFromId() {
		return fromId;
	}
	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public Long getFromComponentId() {
		return fromComponentId;
	}
	public void setFromComponentId(Long fromComponentId) {
		this.fromComponentId = fromComponentId;
	}
	public Integer getLogType() {
		return logType;
	}
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getGiveStatus() {
		return giveStatus;
	}
	public void setGiveStatus(Integer giveStatus) {
		this.giveStatus = giveStatus;
	}
	 
	 
	
}
