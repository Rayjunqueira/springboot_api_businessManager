package com.api.business_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BusinessManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessManagerApiApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return "API is Alive!";
	}
}
