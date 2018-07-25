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
public class UserCarLogs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long userId;
	private Integer carId;
	private Date startTime;
	private Date endTime;
	private Long userCarId;

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

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getUserCarId() {
		return userCarId;
	}

	public void setUserCarId(Long userCarId) {
		this.userCarId = userCarId;
	}

}
