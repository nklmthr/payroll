package com.nklmthr.crm.payroll;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nklmthr.crm.payroll")
public class Application {

	private static Logger logger = Logger.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("Application Started...");
	}

}
