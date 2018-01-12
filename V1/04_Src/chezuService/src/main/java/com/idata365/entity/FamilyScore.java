package com.idata365.entity;

import java.io.Serializable;

public class FamilyScore implements Serializable{

	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
private Long id;
private String month;
private Long familyId;
private Integer orderNo;
private Double score;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public Long getFamilyId() {
	return familyId;
}
public void setFamilyId(Long familyId) {
	this.familyId = familyId;
}
public Integer getOrderNo() {
	return orderNo;
}
public void setOrderNo(Integer orderNo) {
	this.orderNo = orderNo;
}
public Double getScore() {
	return score;
}
public void setScore(Double score) {
	this.score = score;
}



}
