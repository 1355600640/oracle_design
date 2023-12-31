package com.example.oracledemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.oracledemo.mapper")
@ComponentScan("com.example.oracledemo.service")
@ComponentScan("com.example.oracledemo.config")
public class OracleDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OracleDemoApplication.class, args);
	}

}
