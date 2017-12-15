package com.idata365.appService;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.idata365.app.remote")
@MapperScan("com.idata365.app.mapper")
@ComponentScan("com.idata365.app.*")
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class ChezuAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChezuAppServiceApplication.class, args);
	}
}
