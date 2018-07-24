package com.idata365.app.entity.v2;

import java.io.Serializable;

public class DicComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;

	private Long id;
	private Integer   componentId ;// '零件编号',
	private String  componentValue; // '零件名称',
	private String  componentUrl ;// '图片地址',
	private String  quality;// '零件品质',
	private Double   powerAddition;// '零件加成',
	private Integer  travelNum; // '可用行程次数',
	private Integer  currentPower; //'当前动力兑换价',
	private Integer componentType;
	private String componentDesc;
	private Integer power;
	
	
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public String getComponentDesc() {
		return componentDesc;
	}
	public void setComponentDesc(String componentDesc) {
		this.componentDesc = componentDesc;
	}
	public Integer getComponentType() {
		return componentType;
	}
	public void setComponentType(Integer componentType) {
		this.componentType = componentType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getComponentId() {
		return componentId;
	}
	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}
	public String getComponentValue() {
		return componentValue;
	}
	public void setComponentValue(String componentValue) {
		this.componentValue = componentValue;
	}
	public String getComponentUrl() {
		return componentUrl;
	}
	public void setComponentUrl(String componentUrl) {
		this.componentUrl = componentUrl;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public Double getPowerAddition() {
		return powerAddition;
	}
	public void setPowerAddition(Double powerAddition) {
		this.powerAddition = powerAddition;
	}
	public Integer getTravelNum() {
		return travelNum;
	}
	public void setTravelNum(Integer travelNum) {
		this.travelNum = travelNum;
	}
	public Integer getCurrentPower() {
		return currentPower;
	}
	public void setCurrentPower(Integer currentPower) {
		this.currentPower = currentPower;
	}
	 

}
