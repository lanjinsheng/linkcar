package com.idata365.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ChezuEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChezuEurekaServerApplication.class, args);
	}
}
