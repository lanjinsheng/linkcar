package com.idata365.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	SystemProperties systemProperties;

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() throws IOException {
	    CommonsMultipartResolver multipartResolver
	            = new CommonsMultipartResolver();
		multipartResolver.setUploadTempDir(new FileSystemResource(systemProperties.getFileTmpDir()));
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
//	 @Override
//     public void addViewControllers(ViewControllerRegistry registry) {
//	        registry.addViewController("/hello").setViewName("/hello");
//     }
}