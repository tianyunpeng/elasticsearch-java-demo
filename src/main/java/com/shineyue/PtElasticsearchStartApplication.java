package com.shineyue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages={"com.shineyue"})
@ServletComponentScan(basePackages={"com.shineyue"})
@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
public class PtElasticsearchStartApplication {

	public static void main(String[] args) {
		System.setProperty("aip.log4j.conf", "log4j.properties");
		SpringApplication.run(PtElasticsearchStartApplication.class, args);
	}
}
