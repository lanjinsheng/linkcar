package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
    * @ClassName: AssetUsersAsset
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月27日
    *
 */
public class AuctionUsersDiamondsLogs implements Serializable {
	
	    
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 6160116933465753578L;
	private Long id;
	private Long userId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
