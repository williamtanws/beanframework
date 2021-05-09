package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.ImexEntityConverter;
import com.beanframework.imex.domain.Imex;

@Configuration
public class ImexEntityConfig {

	@Autowired
	public ImexEntityConverter imexEntityConverter;

	@Bean
	public ConverterMapping imexEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(imexEntityConverter);
		mapping.setTypeCode(Imex.class.getSimpleName());

		return mapping;
	}
}
