package com.example.SpringProjectsManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.example.SpringProjectsManagement")
@EntityScan("com.example.SpringProjectsManagement.model")
@EnableJpaRepositories("com.example.SpringProjectsManagement.repository")
public class SpringProjectsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProjectsManagementApplication.class, args);
	}

}
