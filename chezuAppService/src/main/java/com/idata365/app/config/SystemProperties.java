package com.idata365.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.idata365.app.partnerApi.SSOTools;
@Configuration
@PropertySource("classpath:system.properties")
@ConfigurationProperties(prefix = "system")
public class SystemProperties {
	public SystemProperties() {
		
	}
	private String fileTmpDir;
    private String jgAppKey;
    private String jgSecret;
    private String product;
    private  String ssoAccessKeyId;
    private  String ssoAccessKeySecret;
	private String ssoQQ;
	private String h5Host;
	private String appHost;
	private String nbcode;
	
	
	public String getAppHost() {
		return appHost;
	}

	public void setAppHost(String appHost) {
		this.appHost = appHost;
	}

	public String getNbcode() {
		return nbcode;
	}

	public void setNbcode(String nbcode) {
		this.nbcode = nbcode;
	}

	public String getH5Host() {
		return h5Host;
	}

	public void setH5Host(String h5Host) {
		this.h5Host = h5Host;
	}

	public String getSsoQQ() {
		return ssoQQ;
	}

	public void setSsoQQ(String ssoQQ) {
		this.ssoQQ = ssoQQ;
	}

	public String getSsoAccessKeyId() {
		return ssoAccessKeyId;
	}

	public void setSsoAccessKeyId(String ssoAccessKeyId) {
		this.ssoAccessKeyId = ssoAccessKeyId;
		SSOTools.accessKeyId=ssoAccessKeyId;
	}

	public String getSsoAccessKeySecret() {
		return ssoAccessKeySecret;
		
	}

	public void setSsoAccessKeySecret(String ssoAccessKeySecret) {
		this.ssoAccessKeySecret = ssoAccessKeySecret;
		SSOTools.accessKeySecret=ssoAccessKeySecret;
	}

	public String getFileTmpDir() {
		return fileTmpDir;
	}

	public void setFileTmpDir(String fileTmpDir) {
		this.fileTmpDir = fileTmpDir;
	}

	public String getJgAppKey() {
		return jgAppKey;
	}

	public void setJgAppKey(String jgAppKey) {
		this.jgAppKey = jgAppKey;
	}

	public String getJgSecret() {
		return jgSecret;
	}

	public void setJgSecret(String jgSecret) {
		this.jgSecret = jgSecret;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
		
	}
	
	
}
