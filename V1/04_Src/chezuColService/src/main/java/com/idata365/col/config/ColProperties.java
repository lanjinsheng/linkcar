package com.idata365.col.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:col.properties")
@ConfigurationProperties(prefix = "col")
public class ColProperties {
	//gps 稳定值
	private Integer gpsHa=100;
	//急加速度阈值
    private Integer jiaSpeedThreshold=2;
    
    //急加速度比值
    private Double  jiaPoint2Value=0.5;
    private Double  jiaPoint3Value=0.4;
    //15s内重复数据过滤
    private Integer  jiaIntervalSecond=15; 

    //急减速度2个阈值区间
    private Integer jianOneSpeed=5;
    private Integer jianTwoSpeed=10;
    //急减速度区间内均值点比值,2组数据
    private Double jianPointOne1Value=1.5;
    private Double jianPointOne2Value=2.0;
    private Double jianPointTwo1Value=1.3;
    private Double jianPointTwo2Value=1.7;
    
    //15s内重复数据过滤
    private Integer  jianIntervalSecond=15; 
    
    //急转取自身前2个点加后5个点进行判断
    private Integer zhuanPrePointNum=2;
    private Integer zhuanNextPointNum=5;
  
    //忽略2点间小于的角度
    private Integer ignoreTwoPointAngle=10;
    
    //处理8点间大于此角度的点
    private Integer dealPointsAngle=60;
    
	//急转速度阈值
    private Integer zhuanSpeedThreshold=7;
    
    //2点差8秒的，就不用算急转了
    private Integer zhuanIgnoreTwoPointTime=8;

    
    //按速率过滤
    private Double zhuanJugeBySpeedRate=1.3;
    
    //按速度与角度过滤
    private Integer zhuanJugeSpeedAndAngleSpeed=10;
    
    private Integer zhuanJugeSpeedAndAngleAngle=30;
    //15s内重复数据过滤
    private Integer  zhuanIntervalSecond=15; 
    
    
    
	public Integer getZhuanIntervalSecond() {
		return zhuanIntervalSecond;
	}

	public void setZhuanIntervalSecond(Integer zhuanIntervalSecond) {
		this.zhuanIntervalSecond = zhuanIntervalSecond;
	}

	public Double getZhuanJugeBySpeedRate() {
		return zhuanJugeBySpeedRate;
	}

	public void setZhuanJugeBySpeedRate(Double zhuanJugeBySpeedRate) {
		this.zhuanJugeBySpeedRate = zhuanJugeBySpeedRate;
	}

	public Integer getZhuanJugeSpeedAndAngleSpeed() {
		return zhuanJugeSpeedAndAngleSpeed;
	}

	public void setZhuanJugeSpeedAndAngleSpeed(Integer zhuanJugeSpeedAndAngleSpeed) {
		this.zhuanJugeSpeedAndAngleSpeed = zhuanJugeSpeedAndAngleSpeed;
	}

	public Integer getZhuanJugeSpeedAndAngleAngle() {
		return zhuanJugeSpeedAndAngleAngle;
	}

	public void setZhuanJugeSpeedAndAngleAngle(Integer zhuanJugeSpeedAndAngleAngle) {
		this.zhuanJugeSpeedAndAngleAngle = zhuanJugeSpeedAndAngleAngle;
	}

	public Integer getZhuanIgnoreTwoPointTime() {
		return zhuanIgnoreTwoPointTime;
	}

	public void setZhuanIgnoreTwoPointTime(Integer zhuanIgnoreTwoPointTime) {
		this.zhuanIgnoreTwoPointTime = zhuanIgnoreTwoPointTime;
	}

	public Integer getZhuanSpeedThreshold() {
		return zhuanSpeedThreshold;
	}

	public void setZhuanSpeedThreshold(Integer zhuanSpeedThreshold) {
		this.zhuanSpeedThreshold = zhuanSpeedThreshold;
	}

	public Integer getIgnoreTwoPointAngle() {
		return ignoreTwoPointAngle;
	}

	public void setIgnoreTwoPointAngle(Integer ignoreTwoPointAngle) {
		this.ignoreTwoPointAngle = ignoreTwoPointAngle;
	}

	public Integer getDealPointsAngle() {
		return dealPointsAngle;
	}

	public void setDealPointsAngle(Integer dealPointsAngle) {
		this.dealPointsAngle = dealPointsAngle;
	}

	public Integer getZhuanPrePointNum() {
		return zhuanPrePointNum;
	}

	public void setZhuanPrePointNum(Integer zhuanPrePointNum) {
		this.zhuanPrePointNum = zhuanPrePointNum;
	}

	public Integer getZhuanNextPointNum() {
		return zhuanNextPointNum;
	}

	public void setZhuanNextPointNum(Integer zhuanNextPointNum) {
		this.zhuanNextPointNum = zhuanNextPointNum;
	}

	public Integer getJianIntervalSecond() {
		return jianIntervalSecond;
	}

	public void setJianIntervalSecond(Integer jianIntervalSecond) {
		this.jianIntervalSecond = jianIntervalSecond;
	}

	public Integer getJianOneSpeed() {
		return jianOneSpeed;
	}

	public void setJianOneSpeed(Integer jianOneSpeed) {
		this.jianOneSpeed = jianOneSpeed;
	}

	public Integer getJianTwoSpeed() {
		return jianTwoSpeed;
	}

	public void setJianTwoSpeed(Integer jianTwoSpeed) {
		this.jianTwoSpeed = jianTwoSpeed;
	}

	public Double getJianPointOne1Value() {
		return jianPointOne1Value;
	}

	public void setJianPointOne1Value(Double jianPointOne1Value) {
		this.jianPointOne1Value = jianPointOne1Value;
	}

	public Double getJianPointOne2Value() {
		return jianPointOne2Value;
	}

	public void setJianPointOne2Value(Double jianPointOne2Value) {
		this.jianPointOne2Value = jianPointOne2Value;
	}

	public Double getJianPointTwo1Value() {
		return jianPointTwo1Value;
	}

	public void setJianPointTwo1Value(Double jianPointTwo1Value) {
		this.jianPointTwo1Value = jianPointTwo1Value;
	}

	public Double getJianPointTwo2Value() {
		return jianPointTwo2Value;
	}

	public void setJianPointTwo2Value(Double jianPointTwo2Value) {
		this.jianPointTwo2Value = jianPointTwo2Value;
	}

	public Double getJiaPoint2Value() {
		return jiaPoint2Value;
	}

	public void setJiaPoint2Value(Double jiaPoint2Value) {
		this.jiaPoint2Value = jiaPoint2Value;
	}

	public Double getJiaPoint3Value() {
		return jiaPoint3Value;
	}

	public void setJiaPoint3Value(Double jiaPoint3Value) {
		this.jiaPoint3Value = jiaPoint3Value;
	}

	public Integer getJiaIntervalSecond() {
		return jiaIntervalSecond;
	}

	public void setJiaIntervalSecond(Integer jiaIntervalSecond) {
		this.jiaIntervalSecond = jiaIntervalSecond;
	}

	public Integer getJiaSpeedThreshold() {
		return jiaSpeedThreshold;
	}

	public void setJiaSpeedThreshold(Integer jiaSpeedThreshold) {
		this.jiaSpeedThreshold = jiaSpeedThreshold;
	}

	public Integer getGpsHa() {
		return gpsHa;
	}

	public void setGpsHa(Integer gpsHa) {
		this.gpsHa = gpsHa;
	} 
	
	
}
