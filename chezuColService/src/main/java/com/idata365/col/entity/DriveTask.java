package com.idata365.col.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @ClassName: DriveTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author LanYeYe
 * @date 2017年11月23日
 *
 */
public class DriveTask  implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private String filePath;
	private int taskStatus;
	private Long driveDataLogId;
	private Date updateTime;
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Long getDriveDataLogId() {
		return driveDataLogId;
	}
	public void setDriveDataLogId(Long driveDataLogId) {
		this.driveDataLogId = driveDataLogId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
