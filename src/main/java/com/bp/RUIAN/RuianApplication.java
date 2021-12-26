package com.bp.RUIAN;

import com.bp.RUIAN.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class RuianApplication {
	@Autowired
	private Environment environment;

	@Autowired
	private CsvService csvService;

	public static void main(String[] args) {
		SpringApplication.run(RuianApplication.class, args);
	}

	@PostConstruct
	private void fillData() {
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			csvService.updateData();
		}
	}
}
