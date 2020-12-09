package com.imti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication //SpringBoot核心注解
@MapperScan("com.imti.mapper") //整合mybatis必须进行映射扫描 orm
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

	}

}
