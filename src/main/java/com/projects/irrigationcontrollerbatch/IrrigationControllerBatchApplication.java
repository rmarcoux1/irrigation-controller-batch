package com.projects.irrigationcontrollerbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IrrigationControllerBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrrigationControllerBatchApplication.class, args);
	}

}
