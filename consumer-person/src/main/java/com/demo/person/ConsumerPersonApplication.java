package com.demo.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerPersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerPersonApplication.class, args);
	}

}
