package com.example.CatchingScammers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class CatchingScammersApplication {
	public static void main(String[] args) {
		FraudDetector fraudDetector = new FraudDetector();
		fraudDetector.detector();
		SpringApplication.run(CatchingScammersApplication.class, args);
	}
}
