package com.beanframework.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.configuration.service.Config;
import com.beanframework.configuration.service.ConfigImpl;

@Configuration
public class ConfigurationConfig {
	
	@Bean(name = "Config")
	public Config config() throws Exception {
		return new ConfigImpl();
	}
}
