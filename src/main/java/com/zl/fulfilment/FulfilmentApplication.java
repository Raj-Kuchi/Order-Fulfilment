package com.zl.fulfilment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.zl.fulfilment"})
@EntityScan(basePackages = {"com.zl.fulfilment"})
public class FulfilmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulfilmentApplication.class, args);
	}
	
	
}
