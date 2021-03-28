package com.beanframework.core.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.core.bean.LocaleMessageBean;
import com.beanframework.core.bean.LocaleMessageBeanImpl;

@Configuration
public class LocaleMessageBeanConfig {

	@Bean(name = "LocaleMessage")
	public LocaleMessageBean LocaleMessageBean() throws Exception {
		return new LocaleMessageBeanImpl();
	}
}
