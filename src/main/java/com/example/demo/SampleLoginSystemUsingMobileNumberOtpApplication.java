package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SampleLoginSystemUsingMobileNumberOtpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleLoginSystemUsingMobileNumberOtpApplication.class, args);
		System.out.println("Server Running");
	}

}
