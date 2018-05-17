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
	private Integer taskFamilyDayOrder;
	private Integer taskFamilyMonthOrder;
	private Integer taskFamilyMonthAvgOrder;
	private Integer userDayScoreFlag;
	private Integer familyDayScoreFlag;
	private String daystamp;
	private Integer taskFamilyLevelDayEnd;
	private Integer taskUserBestDriveDayEnd;
	
	private Integer pkRelation;
	private Integer initPkRelation;
	private Integer userDayScoreInit;
	private Integer nextSeasonInit;
	
	
	public Integer getUserDayScoreInit() {
		return userDayScoreInit;
	}
	public void setUserDayScoreInit(Integer userDayScoreInit) {
		this.userDayScoreInit = userDayScoreInit;
	}
	public Integer getNextSeasonInit() {
		return nextSeasonInit;
	}
	public void setNextSeasonInit(Integer nextSeasonInit) {
		this.nextSeasonInit = nextSeasonInit;
	}
	public Integer getInitPkRelation() {
		return initPkRelation;
	}
	public void setInitPkRelation(Integer initPkRelation) {
		this.initPkRelation = initPkRelation;
	}
	public Integer getPkRelation() {
		return pkRelation;
	}
	public void setPkRelation(Integer pkRelation) {
		this.pkRelation = pkRelation;
	}
	public Integer getTaskUserBestDriveDayEnd() {
		return taskUserBestDriveDayEnd;
	}
	public void setTaskUserBestDriveDayEnd(Integer taskUserBestDriveDayEnd) {
		this.taskUserBestDriveDayEnd = taskUserBestDriveDayEnd;
	}
	public Integer getTaskFamilyLevelDayEnd() {
		return taskFamilyLevelDayEnd;
	}
	public void setTaskFamilyLevelDayEnd(Integer taskFamilyLevelDayEnd) {
		this.taskFamilyLevelDayEnd = taskFamilyLevelDayEnd;
	}
	private String startDay;
	private String endDay;
    private Integer taskGameEnd;
    private Integer taskGameEndInit;
    
	public Integer getTaskGameEnd() {
		return taskGameEnd;
	}
	public void setTaskGameEnd(Integer taskGameEnd) {
		this.taskGameEnd = taskGameEnd;
	}
	public Integer getTaskGameEndInit() {
		return taskGameEndInit;
	}
	public void setTaskGameEndInit(Integer taskGameEndInit) {
		this.taskGameEndInit = taskGameEndInit;
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
	public Integer getTaskFamilyMonthAvgOrder() {
		return taskFamilyMonthAvgOrder;
	}
	public void setTaskFamilyMonthAvgOrder(Integer taskFamilyMonthAvgOrder) {
		this.taskFamilyMonthAvgOrder = taskFamilyMonthAvgOrder;
	}
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
 
	public Integer getTaskFamilyDayOrder() {
		return taskFamilyDayOrder;
	}
	public void setTaskFamilyDayOrder(Integer taskFamilyDayOrder) {
		this.taskFamilyDayOrder = taskFamilyDayOrder;
	}
	public Integer getTaskFamilyMonthOrder() {
		return taskFamilyMonthOrder;
	}
	public void setTaskFamilyMonthOrder(Integer taskFamilyMonthOrder) {
		this.taskFamilyMonthOrder = taskFamilyMonthOrder;
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
