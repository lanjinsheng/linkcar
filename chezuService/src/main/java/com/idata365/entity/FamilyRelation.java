package com.idata365.entity;
/**
 * 
    * @ClassName: FamilyRelation
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月20日
    *
 */

import java.io.Serializable;

public class FamilyRelation  implements Serializable{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Long selfFamilyId;
	private Long competitorFamilyId;
	private String daystamp;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
 
	public Long getSelfFamilyId() {
		return selfFamilyId;
	}
	public void setSelfFamilyId(Long selfFamilyId) {
		this.selfFamilyId = selfFamilyId;
	}
	public Long getCompetitorFamilyId() {
		return competitorFamilyId;
	}
	public void setCompetitorFamilyId(Long competitorFamilyId) {
		this.competitorFamilyId = competitorFamilyId;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	
}
