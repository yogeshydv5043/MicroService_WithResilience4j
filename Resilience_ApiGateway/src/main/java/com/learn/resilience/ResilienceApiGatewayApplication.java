package com.learn.resilience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ResilienceApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResilienceApiGatewayApplication.class, args);
	}

}
