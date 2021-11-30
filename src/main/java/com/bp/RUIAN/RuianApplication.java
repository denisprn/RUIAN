package com.bp.RUIAN;

import com.bp.RUIAN.parsers.CSVFilesParser;
import com.bp.RUIAN.services.EsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

@SpringBootApplication
public class RuianApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuianApplication.class, args);
	}

	/*@Bean
	CommandLineRunner runner(EsService esService) {
		return args -> {
			String csvDirectoryPath = new ClassPathResource(File.separator + "csv" + File.separator)
					.getFile().getAbsolutePath();
			new CSVFilesParser(esService).walk(csvDirectoryPath);
		};
	}*/
}
