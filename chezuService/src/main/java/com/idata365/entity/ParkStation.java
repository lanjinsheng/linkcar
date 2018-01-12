package com.idata365.entity;

import java.io.Serializable;

public class ParkStation  implements Serializable {

	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
private Long id;
private Long familyRelationId;
private Long familyId;
private Long userId;
private String status;
private String updateTime;
private String expireTime;
private Long habitId;

public Long getHabitId() {
	return habitId;
}
public void setHabitId(Long habitId) {
	this.habitId = habitId;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Long getFamilyRelationId() {
	return familyRelationId;
}
public void setFamilyRelationId(Long familyRelationId) {
	this.familyRelationId = familyRelationId;
}
public Long getFamilyId() {
	return familyId;
}
public void setFamilyId(Long familyId) {
	this.familyId = familyId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(String updateTime) {
	this.updateTime = updateTime;
}
public String getExpireTime() {
	return expireTime;
}
public void setExpireTime(String expireTime) {
	this.expireTime = expireTime;
}


}
