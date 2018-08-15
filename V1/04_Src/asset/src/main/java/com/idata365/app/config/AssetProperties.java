package com.idata365.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.idata365.app.constant.AssetConstant;

@Configuration
@PropertySource("classpath:asset.properties")
@ConfigurationProperties(prefix = "asset")
public class AssetProperties {
	public AssetProperties() {
		
	}
	private Integer dayDaimondsAllNet=980;
	public Integer getDayDaimondsAllNet() {
		return dayDaimondsAllNet;
	}
	public void setDayDaimondsAllNet(Integer dayDaimondsAllNet) {
		AssetConstant.DAY_DAIMONDS_FOR_POWER_ALLNET=dayDaimondsAllNet.intValue();
		this.dayDaimondsAllNet = dayDaimondsAllNet;
	}
	
	
    
}
