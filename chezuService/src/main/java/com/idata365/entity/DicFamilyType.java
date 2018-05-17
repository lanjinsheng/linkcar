package com.idata365.entity;

import java.io.Serializable;

public class DicFamilyType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;
	
	private Integer id;
	private String familyTypeValue;
	private Integer familyType;
	private Integer trophy;
	private Integer win;
	private Integer loss;
	private Integer loss2;
	private Integer dogfall;
	private Integer nextExtends;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFamilyTypeValue() {
		return familyTypeValue;
	}
	public void setFamilyTypeValue(String familyTypeValue) {
		this.familyTypeValue = familyTypeValue;
	}
	public Integer getFamilyType() {
		return familyType;
	}
	public void setFamilyType(Integer familyType) {
		this.familyType = familyType;
	}
	public Integer getTrophy() {
		return trophy;
	}
	public void setTrophy(Integer trophy) {
		this.trophy = trophy;
	}
	public Integer getWin() {
		return win;
	}
	public void setWin(Integer win) {
		this.win = win;
	}
	public Integer getLoss() {
		return loss;
	}
	public void setLoss(Integer loss) {
		this.loss = loss;
	}
	public Integer getLoss2() {
		return loss2;
	}
	public void setLoss2(Integer loss2) {
		this.loss2 = loss2;
	}
	public Integer getDogfall() {
		return dogfall;
	}
	public void setDogfall(Integer dogfall) {
		this.dogfall = dogfall;
	}
	public Integer getNextExtends() {
		return nextExtends;
	}
	public void setNextExtends(Integer nextExtends) {
		this.nextExtends = nextExtends;
	}
	
	

}
