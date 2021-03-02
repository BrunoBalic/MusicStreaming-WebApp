package com.itjava.FrontendMicroservice;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class FrontendMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendMicroserviceApplication.class, args);
	}

	@PreDestroy
	public void shutdownUnirest() {
		Unirest.shutDown();
	}

}
