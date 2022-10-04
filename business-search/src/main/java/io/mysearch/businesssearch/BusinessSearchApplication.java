package io.mysearch.businesssearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BusinessSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessSearchApplication.class, args);
	}
}
