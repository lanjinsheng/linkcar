package com.idata365.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@MapperScan("com.idata365.mapper")
@ComponentScan("com.idata365.controller")
public class ChezuServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChezuServiceApplication.class, args);
	}
}
