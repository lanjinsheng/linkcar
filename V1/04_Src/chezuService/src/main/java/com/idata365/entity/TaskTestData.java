package com.idata365.entity;

import java.io.Serializable;

public class TaskTestData implements Serializable{

	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long someId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSomeId() {
		return someId;
	}
	public void setSomeId(Long someId) {
		this.someId = someId;
	}

}
