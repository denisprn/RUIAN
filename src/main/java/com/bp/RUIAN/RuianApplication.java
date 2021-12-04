package com.bp.RUIAN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RuianApplication {
	public static void main(String[] args) {
		SpringApplication.run(RuianApplication.class, args);
	}
}
