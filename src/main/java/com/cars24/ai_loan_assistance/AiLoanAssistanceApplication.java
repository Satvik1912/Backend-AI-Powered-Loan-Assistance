package com.cars24.ai_loan_assistance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Slf4j
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cars24.ai_loan_assistance.data.repositories")
@EntityScan(basePackages = "com.cars24.ai_loan_assistance.data.entities")
public class AiLoanAssistanceApplication {
	public static void main(String[] args) {
		log.info(" Loan Application is starting...");
		SpringApplication.run(AiLoanAssistanceApplication.class, args);
	}
}
