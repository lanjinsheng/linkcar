package com.idata365.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:serviceConstant.properties")
@ConfigurationProperties(prefix = "service")
public class ServiceConstant {
 
	 private Integer threeAlarmTimes0Key=0;
	 private Integer threeAlarmTimes0Score=20;
	 
	 private Integer threeAlarmTimes1Key=1;
	 private Integer threeAlarmTimes1Score=17;
	 
	 private Integer threeAlarmTimes2Key=2;
	 private Integer threeAlarmTimes2Score=12;
	 
	 private Integer threeAlarmTimes3Key=3;
	 private Integer threeAlarmTimes3Score=8;
	 
	 private Integer threeAlarmTimes4Key=4;
	 private Integer threeAlarmTimes4Score=3;
	 
	 
	 private Integer threeAlarmTimes5Key=5;
	 private Integer threeAlarmTimes5Score=-3;
	 
	 private Integer threeAlarmTimes6Key=6;
	 private Integer threeAlarmTimes6Score=-9;
	 
	 private Integer threeAlarmTimes7Key=7;
	 private Integer threeAlarmTimes7Score=-15;
	 
	 private Integer threeAlarmTimes8Key=8;
	 private Integer threeAlarmTimes8Score=-20;
	 
	 private Integer maxAlarmTimes=8;
	 
	 private Integer  protectScore=30;
	 
	 private Integer  tiredHourMaxKey=4;
	 private Double	  tiredHourMaxRate=0.85;
			 
	 private Integer tiredHourGrade1Key=3;
	private Double	  tiredHourGrade1Rate=0.9;

	 private Integer tiredHourGrade2Key=2;
	 private Double	  tiredHourGrade2Rate=0.95;
			 
	private Integer tiredHourGrade3Key=1;
	private Double	  tiredHourGrade3Rate=1.0;	 
	 
	 
	 public Integer getTiredHourMaxKey() {
		return tiredHourMaxKey;
	}
	public void setTiredHourMaxKey(Integer tiredHourMaxKey) {
		this.tiredHourMaxKey = tiredHourMaxKey;
	}
	public Double getTiredHourMaxRate() {
		return tiredHourMaxRate;
	}
	public void setTiredHourMaxRate(Double tiredHourMaxRate) {
		this.tiredHourMaxRate = tiredHourMaxRate;
	}
	public Integer getTiredHourGrade1Key() {
		return tiredHourGrade1Key;
	}
	public void setTiredHourGrade1Key(Integer tiredHourGrade1Key) {
		this.tiredHourGrade1Key = tiredHourGrade1Key;
	}
	public Double getTiredHourGrade1Rate() {
		return tiredHourGrade1Rate;
	}
	public void setTiredHourGrade1Rate(Double tiredHourGrade1Rate) {
		this.tiredHourGrade1Rate = tiredHourGrade1Rate;
	}
	public Integer getTiredHourGrade2Key() {
		return tiredHourGrade2Key;
	}
	public void setTiredHourGrade2Key(Integer tiredHourGrade2Key) {
		this.tiredHourGrade2Key = tiredHourGrade2Key;
	}
	public Double getTiredHourGrade2Rate() {
		return tiredHourGrade2Rate;
	}
	public void setTiredHourGrade2Rate(Double tiredHourGrade2Rate) {
		this.tiredHourGrade2Rate = tiredHourGrade2Rate;
	}
	public Integer getTiredHourGrade3Key() {
		return tiredHourGrade3Key;
	}
	public void setTiredHourGrade3Key(Integer tiredHourGrade3Key) {
		this.tiredHourGrade3Key = tiredHourGrade3Key;
	}
	public Double getTiredHourGrade3Rate() {
		return tiredHourGrade3Rate;
	}
	public void setTiredHourGrade3Rate(Double tiredHourGrade3Rate) {
		this.tiredHourGrade3Rate = tiredHourGrade3Rate;
	}
	static Map<Integer,Integer> ThreeAlarmScoreMap=new HashMap<Integer,Integer>();
 
	 
	 public int getScoreByTimes(Integer times) {
		 if(ThreeAlarmScoreMap.size()==0) {
			 ThreeAlarmScoreMap.put(threeAlarmTimes0Key, threeAlarmTimes0Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes1Key, threeAlarmTimes1Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes2Key, threeAlarmTimes2Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes3Key, threeAlarmTimes3Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes4Key, threeAlarmTimes4Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes5Key, threeAlarmTimes5Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes6Key, threeAlarmTimes6Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes7Key, threeAlarmTimes7Score);
			ThreeAlarmScoreMap.put(threeAlarmTimes8Key, threeAlarmTimes8Score);
		 }
		 return ThreeAlarmScoreMap.get(times);
	 }
	public Integer getProtectScore() {
		return protectScore;
	}
	public void setProtectScore(Integer protectScore) {
		this.protectScore = protectScore;
	}
	public Integer getMaxAlarmTimes() {
		return maxAlarmTimes;
	}
	public void setMaxAlarmTimes(Integer maxAlarmTimes) {
		this.maxAlarmTimes = maxAlarmTimes;
	}
	public Integer getThreeAlarmTimes0Key() {
		return threeAlarmTimes0Key;
	}
	public void setThreeAlarmTimes0Key(Integer threeAlarmTimes0Key) {
		this.threeAlarmTimes0Key = threeAlarmTimes0Key;
	}
	public Integer getThreeAlarmTimes0Score() {
		return threeAlarmTimes0Score;
	}
	public void setThreeAlarmTimes0Score(Integer threeAlarmTimes0Score) {
		this.threeAlarmTimes0Score = threeAlarmTimes0Score;
	}
	public Integer getThreeAlarmTimes1Key() {
		return threeAlarmTimes1Key;
	}
	public void setThreeAlarmTimes1Key(Integer threeAlarmTimes1Key) {
		this.threeAlarmTimes1Key = threeAlarmTimes1Key;
	}
	public Integer getThreeAlarmTimes1Score() {
		return threeAlarmTimes1Score;
	}
	public void setThreeAlarmTimes1Score(Integer threeAlarmTimes1Score) {
		this.threeAlarmTimes1Score = threeAlarmTimes1Score;
	}
	public Integer getThreeAlarmTimes2Key() {
		return threeAlarmTimes2Key;
	}
	public void setThreeAlarmTimes2Key(Integer threeAlarmTimes2Key) {
		this.threeAlarmTimes2Key = threeAlarmTimes2Key;
	}
	public Integer getThreeAlarmTimes2Score() {
		return threeAlarmTimes2Score;
	}
	public void setThreeAlarmTimes2Score(Integer threeAlarmTimes2Score) {
		this.threeAlarmTimes2Score = threeAlarmTimes2Score;
	}
	public Integer getThreeAlarmTimes3Key() {
		return threeAlarmTimes3Key;
	}
	public void setThreeAlarmTimes3Key(Integer threeAlarmTimes3Key) {
		this.threeAlarmTimes3Key = threeAlarmTimes3Key;
	}
	public Integer getThreeAlarmTimes3Score() {
		return threeAlarmTimes3Score;
	}
	public void setThreeAlarmTimes3Score(Integer threeAlarmTimes3Score) {
		this.threeAlarmTimes3Score = threeAlarmTimes3Score;
	}
	public Integer getThreeAlarmTimes4Key() {
		return threeAlarmTimes4Key;
	}
	public void setThreeAlarmTimes4Key(Integer threeAlarmTimes4Key) {
		this.threeAlarmTimes4Key = threeAlarmTimes4Key;
	}
	public Integer getThreeAlarmTimes4Score() {
		return threeAlarmTimes4Score;
	}
	public void setThreeAlarmTimes4Score(Integer threeAlarmTimes4Score) {
		this.threeAlarmTimes4Score = threeAlarmTimes4Score;
	}
	public Integer getThreeAlarmTimes5Key() {
		return threeAlarmTimes5Key;
	}
	public void setThreeAlarmTimes5Key(Integer threeAlarmTimes5Key) {
		this.threeAlarmTimes5Key = threeAlarmTimes5Key;
	}
	public Integer getThreeAlarmTimes5Score() {
		return threeAlarmTimes5Score;
	}
	public void setThreeAlarmTimes5Score(Integer threeAlarmTimes5Score) {
		this.threeAlarmTimes5Score = threeAlarmTimes5Score;
	}
	public Integer getThreeAlarmTimes6Key() {
		return threeAlarmTimes6Key;
	}
	public void setThreeAlarmTimes6Key(Integer threeAlarmTimes6Key) {
		this.threeAlarmTimes6Key = threeAlarmTimes6Key;
	}
	public Integer getThreeAlarmTimes6Score() {
		return threeAlarmTimes6Score;
	}
	public void setThreeAlarmTimes6Score(Integer threeAlarmTimes6Score) {
		this.threeAlarmTimes6Score = threeAlarmTimes6Score;
	}
	public Integer getThreeAlarmTimes7Key() {
		return threeAlarmTimes7Key;
	}
	public void setThreeAlarmTimes7Key(Integer threeAlarmTimes7Key) {
		this.threeAlarmTimes7Key = threeAlarmTimes7Key;
	}
	public Integer getThreeAlarmTimes7Score() {
		return threeAlarmTimes7Score;
	}
	public void setThreeAlarmTimes7Score(Integer threeAlarmTimes7Score) {
		this.threeAlarmTimes7Score = threeAlarmTimes7Score;
	}
	public Integer getThreeAlarmTimes8Key() {
		return threeAlarmTimes8Key;
	}
	public void setThreeAlarmTimes8Key(Integer threeAlarmTimes8Key) {
		this.threeAlarmTimes8Key = threeAlarmTimes8Key;
	}
	public Integer getThreeAlarmTimes8Score() {
		return threeAlarmTimes8Score;
	}
	public void setThreeAlarmTimes8Score(Integer threeAlarmTimes8Score) {
		this.threeAlarmTimes8Score = threeAlarmTimes8Score;
	}
 
	 
}
