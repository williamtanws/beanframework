package com.beanframework.cockpit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.beanframework.cockpit.interceptor.CockpitSecurityInterceptor;

@Configuration
@EnableWebMvc
public class CockpitMvcConfig implements WebMvcConfigurer{

	@Value("${cockpit.webroot}")
	private String COCKPIT_WEBROOT;

	@Bean
	public CockpitSecurityInterceptor cockpitSecurityInterceptor() {
		return new CockpitSecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(cockpitSecurityInterceptor()).addPathPatterns(COCKPIT_WEBROOT+"/**");
	}
}
