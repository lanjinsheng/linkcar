package com.idata365.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DriveScore implements Serializable,Cloneable{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Long id;
	private BigDecimal tiredDrivingScore;
	private BigDecimal nightDrivingScore;
	private BigDecimal maxSpeedScore;
	private BigDecimal overSpeedScore;
	private BigDecimal brakeScore;
	private BigDecimal speedUpScore;
	private BigDecimal turnScore;
	private BigDecimal distanceScore;
	private BigDecimal phonePlayScore;
	private Long userId;
	private Long 	habitId;
	private Date createTime;
	private Integer role;
	private Long familyId;
	private Long userFamilyRoleLogId;
	
	 @Override  
	public Object clone() {  
		 DriveScore score = null;  
	        try{  
	        	score = (DriveScore)super.clone();  
	        }catch(CloneNotSupportedException e) {  
	            e.printStackTrace();  
	        }  
	        return score;  
   }  
	
	public Long getUserFamilyRoleLogId() {
		return userFamilyRoleLogId;
	}

	public void setUserFamilyRoleLogId(Long userFamilyRoleLogId) {
		this.userFamilyRoleLogId = userFamilyRoleLogId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
 
	public BigDecimal getTiredDrivingScore() {
		return tiredDrivingScore;
	}
	public void setTiredDrivingScore(BigDecimal tiredDrivingScore) {
		this.tiredDrivingScore = tiredDrivingScore;
	}
	public BigDecimal getNightDrivingScore() {
		return nightDrivingScore;
	}
	public void setNightDrivingScore(BigDecimal nightDrivingScore) {
		this.nightDrivingScore = nightDrivingScore;
	}
	public BigDecimal getMaxSpeedScore() {
		return maxSpeedScore;
	}
	public void setMaxSpeedScore(BigDecimal maxSpeedScore) {
		this.maxSpeedScore = maxSpeedScore;
	}
	public BigDecimal getOverSpeedScore() {
		return overSpeedScore;
	}
	public void setOverSpeedScore(BigDecimal overSpeedScore) {
		this.overSpeedScore = overSpeedScore;
	}
	public BigDecimal getBrakeScore() {
		return brakeScore;
	}
	public void setBrakeScore(BigDecimal brakeScore) {
		this.brakeScore = brakeScore;
	}
	public BigDecimal getSpeedUpScore() {
		return speedUpScore;
	}
	public void setSpeedUpScore(BigDecimal speedUpScore) {
		this.speedUpScore = speedUpScore;
	}
	public BigDecimal getTurnScore() {
		return turnScore;
	}
	public void setTurnScore(BigDecimal turnScore) {
		this.turnScore = turnScore;
	}
	public BigDecimal getDistanceScore() {
		return distanceScore;
	}
	public void setDistanceScore(BigDecimal distanceScore) {
		this.distanceScore = distanceScore;
	}
	public BigDecimal getPhonePlayScore() {
		return phonePlayScore;
	}
	public void setPhonePlayScore(BigDecimal phonePlayScore) {
		this.phonePlayScore = phonePlayScore;
	}
 
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getHabitId() {
		return habitId;
	}
	public void setHabitId(Long habitId) {
		this.habitId = habitId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	
}
