package com.idata365.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:system.properties")
@ConfigurationProperties(prefix = "system")
public class SystemProperties {
	private String fileTmpDir;

	public String getFileTmpDir() {
		return fileTmpDir;
	}

	public void setFileTmpDir(String fileTmpDir) {
		this.fileTmpDir = fileTmpDir;
	}
	
}
