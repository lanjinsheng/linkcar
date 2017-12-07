package com.idata365.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:system.properties")
@ConfigurationProperties(prefix = "system")
public class SystemProperties {
	private String fileTmpDir;
    private String jgAppKey;
    private String jgSecret;
    
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
	
	
}
