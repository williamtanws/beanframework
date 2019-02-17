package com.beanframework.backoffice.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoMenuConverter;
import com.beanframework.core.converter.DtoMenuFieldConverter;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.data.MenuFieldDto;

@Configuration
public class MenuDtoConfig {

	@Bean
	public DtoMenuConverter dtoMenuConverter() {
		return new DtoMenuConverter();
	}

	@Bean
	public ConverterMapping dtoMenuConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuConverter());
		mapping.setTypeCode(MenuDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DtoMenuFieldConverter dtoMenuFieldConverter() {
		return new DtoMenuFieldConverter();
	}

	@Bean
	public ConverterMapping dtoMenuFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoMenuFieldConverter());
		mapping.setTypeCode(MenuFieldDto.class.getSimpleName());

		return mapping;
	}
}
