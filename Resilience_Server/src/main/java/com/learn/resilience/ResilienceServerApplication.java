package com.learn.resilience;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ResilienceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResilienceServerApplication.class, args);
	}

}
