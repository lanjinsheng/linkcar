package com.idata365.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
    * @ClassName: TempPowerReward
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月27日
    *
 */
public class TempPowerReward implements Serializable {
	
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = -6030749602370574004L;
	private Long id;
	private Long userId;
	private Long powerNum;
	private Long carId;
	private String carType;
	private Date createTime;
	private String uuid;
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public Long getPowerNum() {
		return powerNum;
	}
	public void setPowerNum(Long powerNum) {
		this.powerNum = powerNum;
	}
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	
}
