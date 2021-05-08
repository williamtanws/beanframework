package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.MenuEntityConverter;
import com.beanframework.menu.domain.Menu;

@Configuration
public class MenuEntityConfig {

	@Bean
	public MenuEntityConverter entityMenuConverter() {
		return new MenuEntityConverter();
	}

	@Bean
	public ConverterMapping entityMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityMenuConverter());
		mapping.setTypeCode(Menu.class.getSimpleName());

		return mapping;
	}
}
