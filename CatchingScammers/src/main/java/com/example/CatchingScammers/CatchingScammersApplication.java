package com.example.CatchingScammers;

import com.example.CatchingScammers.db.TextsService;
import com.example.CatchingScammers.db.TextsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class CatchingScammersApplication {
	@Bean
	public TextsService textsService() {
		return new TextsServiceImpl();
	}
	public static void main(String[] args) {
		FraudDetector fraudDetector = new FraudDetector();
		fraudDetector.detector();
		SpringApplication.run(CatchingScammersApplication.class, args);
	}
}
