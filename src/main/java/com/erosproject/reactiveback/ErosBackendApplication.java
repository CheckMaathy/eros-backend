package com.erosproject.reactiveback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ErosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErosBackendApplication.class, args);
	}

}
