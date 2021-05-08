package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.ImexEntityConverter;
import com.beanframework.imex.domain.Imex;

@Configuration
public class ImexEntityConfig {

	@Bean
	public ImexEntityConverter entityImexConverter() {
		return new ImexEntityConverter();
	}

	@Bean
	public ConverterMapping entityImexConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityImexConverter());
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}
}
