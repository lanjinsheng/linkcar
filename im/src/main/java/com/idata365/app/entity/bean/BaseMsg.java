package com.idata365.app.entity.bean;

public class BaseMsg{
	private int messageType;
	private Object datas;
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
	
	
}
