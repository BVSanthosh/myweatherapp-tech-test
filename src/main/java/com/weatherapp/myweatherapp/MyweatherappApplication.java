package com.weatherapp.myweatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the application.
 * This class bootstrap the Spring Boot application by initialising the context and starting the embedded server.
 */
@SpringBootApplication
public class MyweatherappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyweatherappApplication.class, args);
	}
}