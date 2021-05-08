package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.RegionEntityConverter;
import com.beanframework.internationalization.domain.Region;

@Configuration
public class RegionEntityConfig {

	@Bean
	public RegionEntityConverter entityRegionConverter() {
		return new RegionEntityConverter();
	}

	@Bean
	public ConverterMapping entityRegionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityRegionConverter());
		mapping.setTypeCode(Region.class.getSimpleName());

		return mapping;
	}

}
