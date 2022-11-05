package com.leigq.shardingspherestudy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan(basePackages = "com.leigq.shardingspherestudy.domain.mapper")
@SpringBootApplication
public class ShardingsphereStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingsphereStudyApplication.class, args);
	}

}
