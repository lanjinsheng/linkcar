package com.idata365.app.entity.v2;

import java.io.Serializable;
import java.util.Date;

public class ComponentFamily implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long familyId;
	private Integer componentId;
	private Integer componentNum;
	private Date createTime;
	private Integer componentStatus;
	private Integer gainType;
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
	public Integer getComponentStatus() {
		return componentStatus;
	}
	public void setComponentStatus(Integer componentStatus) {
		this.componentStatus = componentStatus;
	}
	public Integer getGainType() {
		return gainType;
	}
	public void setGainType(Integer gainType) {
		this.gainType = gainType;
	}
	 
}
