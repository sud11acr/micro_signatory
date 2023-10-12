package com.project.micro.authorizedsignatories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AuthorizedsignatoriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizedsignatoriesApplication.class, args);
	}

}
