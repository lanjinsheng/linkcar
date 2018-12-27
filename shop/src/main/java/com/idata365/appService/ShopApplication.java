package com.idata365.appService;

import com.idata365.app.util.I18nUtils;
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
@EnableFeignClients("com.idata365.app.remote")
@MapperScan("com.idata365.app.mapper")
@ComponentScan("com.idata365.app.*")
@EnableAutoConfiguration(exclude={MultipartAutoConfiguration.class})
public class ShopApplication {

	public static void main(String[] args) throws Exception {
		I18nUtils.loadLanguage("/data1/soft/shop/languages", "en");
		I18nUtils.setLanguage("en");
		SpringApplication.run(ShopApplication.class, args);
	}
}
