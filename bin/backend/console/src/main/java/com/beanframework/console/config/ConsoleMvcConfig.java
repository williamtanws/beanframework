package com.beanframework.console.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.beanframework.console.interceptor.ConsoleSecurityInterceptor;

@Configuration
@EnableWebMvc
public class ConsoleMvcConfig implements WebMvcConfigurer{
	
	@Value("${console.webroot}")
	private String CONSOLE_WEBROOT;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(consoleSecurityInterceptor()).addPathPatterns(CONSOLE_WEBROOT+"/**");
	}

	@Bean
	public ConsoleSecurityInterceptor consoleSecurityInterceptor() {
		return new ConsoleSecurityInterceptor();
	}
}
