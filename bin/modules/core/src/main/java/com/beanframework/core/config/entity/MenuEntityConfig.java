package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.MenuEntityConverter;
import com.beanframework.menu.domain.Menu;

@Configuration
public class MenuEntityConfig {

	@Autowired
	public MenuEntityConverter menuEntityConverter;

	@Bean
	public ConverterMapping menuEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(menuEntityConverter);
		mapping.setTypeCode(Menu.class.getSimpleName());

		return mapping;
	}
}
