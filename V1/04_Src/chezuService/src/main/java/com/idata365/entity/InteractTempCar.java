package com.idata365.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
    * @ClassName: InteractTempPower
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年4月27日
    *
 */
public class InteractTempCar implements Serializable {
	
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = -6030749602370574004L;
	private Long id;
	private Long userId;
	private Long powerNum=0L;
	private Long carId=0L;
	private String carType;
	private Integer type=-1;
	private Date createTime;
	private String uuid;
	private Integer hadGet;
	private String name="";
	private String daystamp;
	private Long lockTime;
	private Long stealerId;
	private String stealer;
	private Integer lockStatus;
	private String lockBatchId;
	private String blackIds;
	private Long travelId;
	private Integer r=0;
	
	
	public Integer getR() {
		return r;
	}
	public void setR(Integer r) {
		this.r = r;
	}
	public Long getTravelId() {
		return travelId;
	}
	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}
	public String getBlackIds() {
		return blackIds;
	}
	public void setBlackIds(String blackIds) {
		this.blackIds = blackIds;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	public Long getLockTime() {
		return lockTime;
	}
	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
	}
	public Long getStealerId() {
		return stealerId;
	}
	public void setStealerId(Long stealerId) {
		this.stealerId = stealerId;
	}
	public String getStealer() {
		return stealer;
	}
	public void setStealer(String stealer) {
		this.stealer = stealer;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getLockBatchId() {
		return lockBatchId;
	}
	public void setLockBatchId(String lockBatchId) {
		this.lockBatchId = lockBatchId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getHadGet() {
		return hadGet;
	}
	public void setHadGet(Integer hadGet) {
		this.hadGet = hadGet;
	}
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
