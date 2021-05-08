package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.core.converter.dto.AuditorDtoConverter;
import com.beanframework.core.converter.populator.AuditorPopulator;

@Configuration
public class AuditorDtoConfig {

	@Bean
	public AuditorPopulator auditorPopulator() {
		return new AuditorPopulator();
	}

	@Bean
	public AuditorDtoConverter dtoAuditorConverter() {
		AuditorDtoConverter converter = new AuditorDtoConverter();
		converter.addPopulator(auditorPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoAuditorConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAuditorConverter());
		mapping.setTypeCode(AuditorDto.class.getSimpleName());

		return mapping;
	}
}
