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
	private Integer inUse;
	private Date createTime;
	private Integer componentStatus;
	private Integer leftTravelNum;
	private Integer gainType;
	private Integer carId;
	
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
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
	public Integer getInUse() {
		return inUse;
	}
	public void setInUse(Integer inUse) {
		this.inUse = inUse;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getComponentStatus() {
		return componentStatus;
	}
	public void setComponentStatus(Integer componentStatus) {
		this.componentStatus = componentStatus;
	}
	public Integer getLeftTravelNum() {
		return leftTravelNum;
	}
	public void setLeftTravelNum(Integer leftTravelNum) {
		this.leftTravelNum = leftTravelNum;
	}
	public Integer getGainType() {
		return gainType;
	}
	public void setGainType(Integer gainType) {
		this.gainType = gainType;
	}
	 
	
}
