package com.idata365.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver 
	            = new CommonsMultipartResolver();
	    return multipartResolver;
	}
	@Override  
	public void addCorsMappings(CorsRegistry registry) {  
	        registry.addMapping("/**")  
	                .allowedOrigins("*")  
	                .allowCredentials(true)  
	                .allowedMethods("GET", "POST", "DELETE", "PUT")  
	                .maxAge(3600);  
	}  
}