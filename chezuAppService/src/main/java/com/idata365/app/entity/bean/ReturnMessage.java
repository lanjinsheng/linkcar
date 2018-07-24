package com.idata365.app.entity.bean;

import java.util.HashMap;
import java.util.Map;

public class ReturnMessage {
	private int status=1;
	private String msg="";
	private Map<String,Object> data=new HashMap<>();
	
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
