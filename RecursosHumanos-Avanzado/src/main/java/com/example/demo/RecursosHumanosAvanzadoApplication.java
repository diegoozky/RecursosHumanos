package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages= {"Controller"})
@EntityScan(basePackages= {"Model"})
@EnableJpaRepositories(basePackages= {"Repositorios"})
public class RecursosHumanosAvanzadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecursosHumanosAvanzadoApplication.class, args);
	}

}
