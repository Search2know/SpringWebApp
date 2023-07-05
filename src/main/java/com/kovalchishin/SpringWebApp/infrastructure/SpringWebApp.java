package com.kovalchishin.SpringWebApp.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringWebApp {

	@Bean
	CommandLineRunner runner(MongoFileService service) {
		return args -> {
			System.out.println("Hello\n");
			System.out.print("\nType the desired command:\n create file [text to add]\n show\n update [position of file] [text description updating]\n delete [position of file] \n exit\n");

			try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
				var done = false;
				do {
					var cmd = reader.readLine();
					if (cmd.equals("exit")) {
						done = true;
					} else if (cmd.equals("show")) {
						service.findAll().forEach(System.out::println);
					} else {
						var pattern = Pattern.compile("(\\w+)\\s(\\d+|\\w+)(\\s(.*))?");
						var matcher = pattern.matcher(cmd);
						if (matcher.find()) {
							try {
								if (matcher.group(1).equals("create")) {
									service.create(matcher.group(4));
								} else if (Integer.parseInt(matcher.group(2)) <= service.nextPosition() - 1) {
									if (matcher.group(1).equals("update")) {
										service.update(Integer.parseInt(matcher.group(2)), matcher.group(4));
									}
									if (matcher.group(1).equals("delete")) {
										service.delete(Integer.parseInt(matcher.group(2)));
									}
								} else {
									throw new IllegalArgumentException("Provided line number is out of bounds");
								}

							} catch (IllegalArgumentException e) {
								System.out.println(e.getMessage());
							}
						} else {
							System.out.println("Improper command");
						}
					}
				} while (!done);
			} catch (IOException e) {
				System.out.println("Wrong input command.");
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApp.class, args);
	}
}


