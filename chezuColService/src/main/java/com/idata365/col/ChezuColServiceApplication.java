package com.idata365.col;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.idata365.col.remote")
@MapperScan("com.idata365.col.mapper")
@ComponentScan("com.idata365.col.*")
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class ChezuColServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChezuColServiceApplication.class, args);
	}
}
