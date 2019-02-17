package com.beanframework.backoffice.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoLanguageConverter;
import com.beanframework.core.data.LanguageDto;

@Configuration
public class LanguageDtoConfig {

	@Bean
	public DtoLanguageConverter dtoLanguageConverter() {
		return new DtoLanguageConverter();
	}

	@Bean
	public ConverterMapping dtoLanguageConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoLanguageConverter());
		mapping.setTypeCode(LanguageDto.class.getSimpleName());

		return mapping;
	}
}
