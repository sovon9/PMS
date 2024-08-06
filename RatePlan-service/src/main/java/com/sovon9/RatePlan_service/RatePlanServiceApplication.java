package com.sovon9.RatePlan_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RatePlanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatePlanServiceApplication.class, args);
	}

}
