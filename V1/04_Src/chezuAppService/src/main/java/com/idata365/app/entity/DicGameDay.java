package com.idata365.app.entity;

import java.io.Serializable;

public class DicGameDay implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3249981262869758886L;
	private Integer id;
	private String startDay;
	private String endDay;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
 
	
	

}
