package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoCronjobConverter;
import com.beanframework.core.converter.dto.DtoCronjobDataConverter;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;

@Configuration
public class CronjobDtoConfig {

	@Bean
	public DtoCronjobConverter dtoCronjobConverter() {
		return new DtoCronjobConverter();
	}

	@Bean
	public ConverterMapping dtoCronjobConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCronjobConverter());
		mapping.setTypeCode(CronjobDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoCronjobDataConverter dtoCronjobDataConverter() {
		return new DtoCronjobDataConverter();
	}

	@Bean
	public ConverterMapping dtoCronjobDataConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCronjobDataConverter());
		mapping.setTypeCode(CronjobDataDto.class.getSimpleName());

		return mapping;
	}

}