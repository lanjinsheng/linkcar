package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
    * @ClassName: AssetFamiliesDiamondsLogs
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月27日
    *
 */
public class AssetFamiliesDiamondsLogs implements Serializable {
	
	    
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = -3036562359948116746L;
	private Long id;
	private Long familyId;
	private BigDecimal diamondsNum;
	private Integer recordType;
	private Integer eventType;
	private Long effectId;
	private String remark;
	private Date createTime;
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
	public BigDecimal getDiamondsNum() {
		return diamondsNum;
	}
	public void setDiamondsNum(BigDecimal diamondsNum) {
		this.diamondsNum = diamondsNum;
	}
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public Long getEffectId() {
		return effectId;
	}
	public void setEffectId(Long effectId) {
		this.effectId = effectId;
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
