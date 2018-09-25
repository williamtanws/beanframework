package com.beanframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication(scanBasePackages = { "${spring.scanBasePackages}" }, exclude = {
		SecurityAutoConfiguration.class }) // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PlatformApplication.class);

		// register PID write to spring boot. It will write PID to file
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);
	}
}
