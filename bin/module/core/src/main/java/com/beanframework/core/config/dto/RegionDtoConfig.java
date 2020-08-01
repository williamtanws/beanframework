package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoRegionConverter;
import com.beanframework.core.data.RegionDto;

@Configuration
public class RegionDtoConfig {

	@Bean
	public DtoRegionConverter dtoRegionConverter() {
		return new DtoRegionConverter();
	}

	@Bean
	public ConverterMapping dtoRegionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoRegionConverter());
		mapping.setTypeCode(RegionDto.class.getSimpleName());

		return mapping;
	}
}
