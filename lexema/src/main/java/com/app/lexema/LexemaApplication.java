package com.app.lexema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LexemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LexemaApplication.class, args);
	}

}
