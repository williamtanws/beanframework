package com.beanframework.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.core.bean.MenuNavigationBean;
import com.beanframework.core.bean.MenuNavigationBeanImpl;

@Configuration
public class BackofficeBeanConfig {
	
	@Bean(name = "MenuNavigation")
	public MenuNavigationBean config() throws Exception {
		return new MenuNavigationBeanImpl();
	}
}
