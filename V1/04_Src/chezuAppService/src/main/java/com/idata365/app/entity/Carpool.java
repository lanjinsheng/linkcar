/**
 * 
 */
package com.idata365.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jinsheng
 *
 */
public class Carpool implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long driverId;
	private Long passengerId;
	private Integer carId;
	private Long userCarLogsId;
	private Date createTime;
	private Date getOffTime;
	private Integer isComplete;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public Long getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public Long getUserCarLogsId() {
		return userCarLogsId;
	}
	public void setUserCarLogsId(Long userCarLogsId) {
		this.userCarLogsId = userCarLogsId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getGetOffTime() {
		return getOffTime;
	}
	public void setGetOffTime(Date getOffTime) {
		this.getOffTime = getOffTime;
	}
	public Integer getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}
	

}
