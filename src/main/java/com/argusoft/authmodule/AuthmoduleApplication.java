package com.argusoft.authmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthmoduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthmoduleApplication.class, args);
	}

}
