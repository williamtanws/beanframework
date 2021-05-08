package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.CronjobDtoConverter;
import com.beanframework.core.converter.populator.CronjobPopulator;
import com.beanframework.core.data.CronjobDto;

@Configuration
public class CronjobDtoConfig {

	@Bean
	public CronjobPopulator cronjobPopulator() {
		return new CronjobPopulator();
	}

	@Bean
	public CronjobDtoConverter dtoCronjobConverter() {
		CronjobDtoConverter converter = new CronjobDtoConverter();
		converter.addPopulator(cronjobPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCronjobConverter());
		mapping.setTypeCode(CronjobDto.class.getSimpleName());

		return mapping;
	}
}
