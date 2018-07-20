package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Date;

public class BoxTreasureUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String boxId;
	private Integer componentId;
	private Integer componentNum;
	private Date createTime;
	private Date receiveTime;
	private Long toUserId;
	private Integer gainType;
	private Long travelId;
	
	public Long getTravelId() {
		return travelId;
	}
	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	 
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public Integer getComponentNum() {
		return componentNum;
	}
	public void setComponentNum(Integer componentNum) {
		this.componentNum = componentNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
 
	public Long getToUserId() {
		return toUserId;
	}
	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getGainType() {
		return gainType;
	}
	public void setGainType(Integer gainType) {
		this.gainType = gainType;
	}
	
	
}
