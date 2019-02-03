package com.beanframework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.core.bean.ConfigBean;
import com.beanframework.core.bean.ConfigBeanImpl;

@Configuration
public class ConfigurationBeanConfig {
	
	@Bean(name = "Config")
	public ConfigBean ConfigBean() throws Exception {
		return new ConfigBeanImpl();
	}
}
