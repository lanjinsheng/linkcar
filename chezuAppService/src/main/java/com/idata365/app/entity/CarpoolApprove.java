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
public class CarpoolApprove implements Serializable{

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
	private Date approveTime;
	private Integer approveStatus;
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
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public Integer getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}
 
 
}
