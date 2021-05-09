package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.ImexDtoConverter;
import com.beanframework.core.converter.populator.ImexPopulator;
import com.beanframework.core.data.ImexDto;

@Configuration
public class ImexDtoConfig {

	@Autowired
	public ImexPopulator imexPopulator;

	@Bean
	public ImexDtoConverter imexDtoConverter() {
		ImexDtoConverter converter = new ImexDtoConverter();
		converter.addPopulator(imexPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping imexDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(imexDtoConverter());
		mapping.setTypeCode(ImexDto.class.getSimpleName());

		return mapping;
	}
}
