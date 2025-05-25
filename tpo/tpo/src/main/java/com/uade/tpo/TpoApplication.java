package com.uade.tpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.uade.tpo")
@EnableScheduling
public class TpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpoApplication.class, args);
	}

}
