package com.beanframework.documentation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.beanframework.documentation.DocumentationConstants;

@Configuration
@EnableWebSecurity
@Order(3)
public class DocumentationSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.requestMatchers()
    			.antMatchers(DocumentationConstants.Path.DOCUMENTATION+"/**")
    			.and()
	        .authorizeRequests()
	        	.antMatchers(DocumentationConstants.Path.DOCUMENTATION+"/**").authenticated()
	        	.antMatchers(DocumentationConstants.Path.DOCUMENTATION+"/**").hasAnyAuthority(DocumentationConstants.DocumentationPreAuthorizeEnum.DOCUMENTATION_READ);
    	http.headers().frameOptions().sameOrigin();
    }
}