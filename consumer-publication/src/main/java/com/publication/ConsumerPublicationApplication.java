package com.publication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerPublicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerPublicationApplication.class, args);
	}

}
