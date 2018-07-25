package com.idata365.entity;

import java.io.Serializable;
import java.util.Date;

public class BoxTreasureFamily implements Serializable{
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
	private Long toFamilyId;
	private Integer gainType;
	private Integer hadGet;
	private String daystamp;
	
	public BoxTreasureFamily() {
		super();
	}
	
	public BoxTreasureFamily(String daystamp,String boxId,Long toFamilyId) {
		this.daystamp=daystamp;
		this.boxId=boxId;
		this.toFamilyId=toFamilyId;
	}
	public Integer getHadGet() {
		return hadGet;
	}
	public void setHadGet(Integer hadGet) {
		this.hadGet = hadGet;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
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
	public Long getToFamilyId() {
		return toFamilyId;
	}
	public void setToFamilyId(Long toFamilyId) {
		this.toFamilyId = toFamilyId;
	}
	public Integer getGainType() {
		return gainType;
	}
	public void setGainType(Integer gainType) {
		this.gainType = gainType;
	}
	
	
}
