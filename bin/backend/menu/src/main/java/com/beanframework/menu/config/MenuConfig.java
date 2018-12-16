package com.beanframework.menu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.menu.converter.DtoMenuConverter;
import com.beanframework.menu.converter.EntityMenuConverter;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.interceptor.MenuInitialDefaultsInterceptor;
import com.beanframework.menu.interceptor.MenuLoadInterceptor;
import com.beanframework.menu.interceptor.MenuPrepareInterceptor;
import com.beanframework.menu.interceptor.MenuRemoveInterceptor;
import com.beanframework.menu.interceptor.MenuValidateInterceptor;

@Configuration
public class MenuConfig {

	@Bean
	public DtoMenuConverter dtoMenuConverter() {
		return new DtoMenuConverter();
	}
	
	@Bean
	public ConverterMapping dtoMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuConverter());
		mapping.setTypeCode(Menu.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public EntityMenuConverter entityMenuConverter() {
		return new EntityMenuConverter();
	}
	
	@Bean
	public ConverterMapping entityMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityMenuConverter());
		mapping.setTypeCode(Menu.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public MenuInitialDefaultsInterceptor menuInitialDefaultsInterceptor() {
		return new MenuInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping menuInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(menuInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Menu.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public MenuValidateInterceptor menuValidateInterceptor() {
		return new MenuValidateInterceptor();
	}

	@Bean
	public InterceptorMapping menuValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(menuValidateInterceptor());
		interceptorMapping.setTypeCode(Menu.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public MenuPrepareInterceptor menuPrepareInterceptor() {
		return new MenuPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping menuPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(menuPrepareInterceptor());
		interceptorMapping.setTypeCode(Menu.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public MenuLoadInterceptor menuLoadInterceptor() {
		return new MenuLoadInterceptor();
	}

	@Bean
	public InterceptorMapping menuLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(menuLoadInterceptor());
		interceptorMapping.setTypeCode(Menu.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public MenuRemoveInterceptor menuRemoveInterceptor() {
		return new MenuRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping menuRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(menuRemoveInterceptor());
		interceptorMapping.setTypeCode(Menu.class.getSimpleName());
		
		return interceptorMapping;
	}
		
}
