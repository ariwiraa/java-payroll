package com.example.springbootpayroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.springbootpayroll.model.entity")
public class SpringbootPayrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPayrollApplication.class, args);
	}

}
