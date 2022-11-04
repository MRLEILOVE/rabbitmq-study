package com.leigq.shardingspherestudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.leigq.shardingspherestudy.domain.mapper")
@SpringBootApplication
public class ShardingsphereStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingsphereStudyApplication.class, args);
	}

}