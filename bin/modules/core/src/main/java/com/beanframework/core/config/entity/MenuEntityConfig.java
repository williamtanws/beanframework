package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityMenuConverter;
import com.beanframework.menu.domain.Menu;

@Configuration
public class MenuEntityConfig {

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
}
