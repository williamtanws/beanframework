package com.beanframework.core.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.EntityRegionConverter;
import com.beanframework.internationalization.domain.Region;

@Configuration
public class RegionEntityConfig {

	@Bean
	public EntityRegionConverter entityRegionConverter() {
		return new EntityRegionConverter();
	}

	@Bean
	public ConverterMapping entityRegionConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityRegionConverter());
		mapping.setTypeCode(Region.class.getSimpleName());

		return mapping;
	}

}
