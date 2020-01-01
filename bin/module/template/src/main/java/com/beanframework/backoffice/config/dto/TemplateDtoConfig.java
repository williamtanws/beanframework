package com.beanframework.backoffice.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoTemplateConverter;
import com.beanframework.core.data.TemplateDto;

@Configuration
public class TemplateDtoConfig {

	@Bean
	public DtoTemplateConverter dtoTemplateConverter() {
		return new DtoTemplateConverter();
	}

	@Bean
	public ConverterMapping dtoTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoTemplateConverter());
		mapping.setTypeCode(TemplateDto.class.getSimpleName());

		return mapping;
	}
}
