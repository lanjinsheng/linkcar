package com.idata365.entity;

import java.io.Serializable;

public class TaskSystemScoreFlag implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer taskFamilyInit;
	private Integer taskFamilyPkInit;
	private Integer taskFamilyOrderInit;
	private Integer taskFamilyPk;
	private Integer taskFamilyOrder;
	private Integer userDayScoreFlag;
	private Integer familyDayScoreFlag;
	private String daystamp;
	
	
	
	public Integer getTaskFamilyPkInit() {
		return taskFamilyPkInit;
	}
	public void setTaskFamilyPkInit(Integer taskFamilyPkInit) {
		this.taskFamilyPkInit = taskFamilyPkInit;
	}
	public Integer getTaskFamilyOrderInit() {
		return taskFamilyOrderInit;
	}
	public void setTaskFamilyOrderInit(Integer taskFamilyOrderInit) {
		this.taskFamilyOrderInit = taskFamilyOrderInit;
	}
	public Integer getTaskFamilyPk() {
		return taskFamilyPk;
	}
	public void setTaskFamilyPk(Integer taskFamilyPk) {
		this.taskFamilyPk = taskFamilyPk;
	}
	public Integer getTaskFamilyOrder() {
		return taskFamilyOrder;
	}
	public void setTaskFamilyOrder(Integer taskFamilyOrder) {
		this.taskFamilyOrder = taskFamilyOrder;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTaskFamilyInit() {
		return taskFamilyInit;
	}
	public void setTaskFamilyInit(Integer taskFamilyInit) {
		this.taskFamilyInit = taskFamilyInit;
	}
	public Integer getUserDayScoreFlag() {
		return userDayScoreFlag;
	}
	public void setUserDayScoreFlag(Integer userDayScoreFlag) {
		this.userDayScoreFlag = userDayScoreFlag;
	}
	public Integer getFamilyDayScoreFlag() {
		return familyDayScoreFlag;
	}
	public void setFamilyDayScoreFlag(Integer familyDayScoreFlag) {
		this.familyDayScoreFlag = familyDayScoreFlag;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
	
	

}
