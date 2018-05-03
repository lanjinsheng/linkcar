package com.idata365.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.idata365.*")
@EnableDiscoveryClient
@EnableFeignClients("com.idata365.remote")
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class ChezuServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChezuServiceApplication.class, args);
	}
}
