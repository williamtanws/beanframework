package com.beanframework.backoffice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.beanframework.backoffice.interceptor.BackofficeSecurityInterceptor;

@Configuration
@EnableCaching
@EnableWebMvc
//@ComponentScan(basePackages = { "com.beanframework.backoffice" })
public class BackofficeMvcConfig implements WebMvcConfigurer {

	@Value("${backoffice.webroot}")
	private String BACKOFFICE_WEBROOT;

	@Bean
	public BackofficeSecurityInterceptor backofficeSecurityInterceptor() {
		return new BackofficeSecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(backofficeSecurityInterceptor()).addPathPatterns(BACKOFFICE_WEBROOT + "/**");
	}
}
