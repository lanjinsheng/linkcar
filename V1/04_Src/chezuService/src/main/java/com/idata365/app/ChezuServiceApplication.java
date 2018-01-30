package com.idata365.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@ComponentScan("com.idata365.*")
@EnableFeignClients("com.idata365.remote")
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class ChezuServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChezuServiceApplication.class, args);
	}
}
