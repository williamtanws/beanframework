package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoAdminConverter;
import com.beanframework.core.data.AdminDto;

@Configuration
public class AdminDtoConfig {

	@Bean
	public DtoAdminConverter dtoAdminConverter() {
		return new DtoAdminConverter();
	}

	@Bean
	public ConverterMapping dtoAdminConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAdminConverter());
		mapping.setTypeCode(AdminDto.class.getSimpleName());

		return mapping;
	}
}
