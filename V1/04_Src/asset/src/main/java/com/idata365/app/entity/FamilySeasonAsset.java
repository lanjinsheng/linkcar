package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
    * @ClassName: FamilyGameAsset
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月27日
    *
 */
public class FamilySeasonAsset implements Serializable {
	
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 583367340713641367L;
	private Long id;
	private Long familyId;
	private Integer familyType;
	private Integer trophy;
	private String startDay;
	private String endDay;
	private Long orderNo;
	private BigDecimal diamondsNum;
	private String seasonName;
	private Integer memberNum;
	
	
	public Integer getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
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
	public String getStartDay() {
		return startDay;
	}
	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public BigDecimal getDiamondsNum() {
		return diamondsNum;
	}
	public void setDiamondsNum(BigDecimal diamondsNum) {
		this.diamondsNum = diamondsNum;
	}
	public String getSeasonName() {
		return seasonName;
	}
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}
	
	
}
