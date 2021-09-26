package com.bp.RUIAN;

import com.bp.RUIAN.parsers.FilesParser;
import com.bp.RUIAN.services.EsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import java.io.File;

/**
 * Main class
 * @author Denys Peresychanskyi
 */
@SpringBootApplication
public class RuianApplication {

	/**
	 * Main method
	 * @param args application arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(RuianApplication.class, args);
	}

	/**
	 * Main method for parsing XML file
	 * @param esService Elasticsearch service
	 */
	@Bean
	CommandLineRunner runner(EsService esService) {
		return args -> {
			//Parse xml files and import to Elasticsearch
			File xmlDirectory = new ClassPathResource("/xml/").getFile();
			String xmlDirectoryPath = xmlDirectory.getAbsolutePath();

			//Invoke custom files parsing class
			FilesParser filesParser = new FilesParser(esService);
			filesParser.walk(xmlDirectoryPath);
		};
	}
}
