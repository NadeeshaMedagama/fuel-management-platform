package com.FuelBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.FuelBackend")
public class FuelBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuelBackendApplication.class, args);
	}
}
