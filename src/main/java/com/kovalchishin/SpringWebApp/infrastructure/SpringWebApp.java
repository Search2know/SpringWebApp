package com.kovalchishin.SpringWebApp.infrastructure;

import com.kovalchishin.SpringWebApp.domain.FileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;



@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringWebApp {

	@Bean
	CommandLineRunner runner(FileService fileService) {
		return new CommandRunner(fileService);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApp.class, args);
	}
}


