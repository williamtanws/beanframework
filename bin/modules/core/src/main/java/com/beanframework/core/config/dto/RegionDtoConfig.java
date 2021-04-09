package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoRegionConverter;
import com.beanframework.core.converter.populator.RegionPopulator;
import com.beanframework.core.data.RegionDto;

@Configuration
public class RegionDtoConfig {

	@Bean
	public RegionPopulator regionPopulator() {
		return new RegionPopulator();
	}

	@Bean
	public DtoRegionConverter dtoRegionConverter() {
		DtoRegionConverter converter = new DtoRegionConverter();
		converter.addPopulator(regionPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoRegionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoRegionConverter());
		mapping.setTypeCode(RegionDto.class.getSimpleName());

		return mapping;
	}
}