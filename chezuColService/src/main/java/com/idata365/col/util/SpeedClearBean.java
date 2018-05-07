package com.idata365.col.util;

public class SpeedClearBean implements Comparable<SpeedClearBean>{
	
	
	public Double speed=0d;
	public Integer lsNumber=0;
	
	public SpeedClearBean(Double paraSpeed,Integer paraLsNumber) {
		this.speed=paraSpeed;
		this.lsNumber=paraLsNumber;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Integer getLsNumber() {
		return lsNumber;
	}
	public void setLsNumber(Integer lsNumber) {
		this.lsNumber = lsNumber;
	}
	
	
	@Override
	public int compareTo(SpeedClearBean s) {
    //自定义比较方法，如果认为此实体本身大则返回1，否则返回-1
	    if(this.speed > s.getSpeed()){
	        return -1;
	    }else if(this.speed<s.getSpeed()) {
	      return 1;
	    }else {
	    	return 0;
	    }
	}
	

}
