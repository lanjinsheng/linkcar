package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Date;

public class ComponentUserUseLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private Integer componentId;
	private Long userComponentId;
	private Integer eventType;
	private Date eventTime;
	private Long userCarId;
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
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public Long getUserComponentId() {
		return userComponentId;
	}
	public void setUserComponentId(Long userComponentId) {
		this.userComponentId = userComponentId;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public Date getEventTime() {
		return eventTime;
	}
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	public Long getUserCarId() {
		return userCarId;
	}
	public void setUserCarId(Long userCarId) {
		this.userCarId = userCarId;
	}
	
  
}
