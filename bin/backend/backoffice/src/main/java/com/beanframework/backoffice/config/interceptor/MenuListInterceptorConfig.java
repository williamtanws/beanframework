package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.interceptor.MenuListLoadInterceptor;

@Configuration
public class MenuListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public MenuListLoadInterceptor menuListLoadInterceptor() {
		return new MenuListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping menuListLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(menuListLoadInterceptor());
		interceptorMapping.setTypeCode(Menu.class.getSimpleName() + "List");

		return interceptorMapping;
	}

}
