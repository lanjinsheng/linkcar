package com.idata365.app.entity;

import java.io.Serializable;

public class TaskSystemFlag implements Serializable {

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer taskLotteryInit;
	private String daystamp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTaskLotteryInit() {
		return taskLotteryInit;
	}
	public void setTaskLotteryInit(Integer taskLotteryInit) {
		this.taskLotteryInit = taskLotteryInit;
	}
	public String getDaystamp() {
		return daystamp;
	}
	public void setDaystamp(String daystamp) {
		this.daystamp = daystamp;
	}
 
	
	
	

}
