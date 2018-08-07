package com.idata365.appService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.idata365.*")
public class ZuulApplication {


	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}



}
