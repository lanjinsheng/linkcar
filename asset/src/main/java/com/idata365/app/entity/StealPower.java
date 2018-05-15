package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
    * @ClassName: StealPower
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年5月11日
    *
 */
public class StealPower implements Serializable {
 
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 4131253528692066109L;
	private Long id;
	private Long userId;
	private Long familyId;
	private String remark;
	private Integer powerNum;
	private Date createTime;
	private String daystamp;
	private Long ballId;
	private Long fightFamilyId;
	
	public Long getFightFamilyId() {
		return fightFamilyId;
	}
	public void setFightFamilyId(Long fightFamilyId) {
		this.fightFamilyId = fightFamilyId;
	}
	public Integer getPowerNum() {
		return powerNum;
	}
	public void setPowerNum(Integer powerNum) {
		this.powerNum = powerNum;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Long getBallId() {
		return ballId;
	}
	public void setBallId(Long ballId) {
		this.ballId = ballId;
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
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
 
 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
