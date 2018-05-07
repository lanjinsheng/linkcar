package com.idata365.col.util;

public class OrderGpsBean implements Comparable<OrderGpsBean>{
	
	
	public String time="0000-00-00 00:00:00.000";
	public Integer lsNumber=0;
	public OrderGpsBean(String pTime,Integer plsNumber) {
		this.time=pTime;
		this.lsNumber=plsNumber;
	}
 
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getLsNumber() {
		return lsNumber;
	}
	public void setLsNumber(Integer lsNumber) {
		this.lsNumber = lsNumber;
	}
	
	
	@Override
	public int compareTo(OrderGpsBean s) {
    //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
	    return (this.time.compareTo(s.getTime()));
	      
	}
	

}
