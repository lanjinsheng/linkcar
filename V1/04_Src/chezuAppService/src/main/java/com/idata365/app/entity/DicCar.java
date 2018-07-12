package com.idata365.app.entity;

import java.io.Serializable;

public class DicCar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9222315647447481118L;
	
	private Integer id;
	private Integer carId;
	private String carName;
	private Integer carSeat;
	private String carUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public Integer getCarSeat() {
		return carSeat;
	}
	public void setCarSeat(Integer carSeat) {
		this.carSeat = carSeat;
	}
	public String getCarUrl() {
		return carUrl;
	}
	public void setCarUrl(String carUrl) {
		this.carUrl = carUrl;
	}
	

}
