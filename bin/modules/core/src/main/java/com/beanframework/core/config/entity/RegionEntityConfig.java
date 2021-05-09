package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.RegionEntityConverter;
import com.beanframework.internationalization.domain.Region;

@Configuration
public class RegionEntityConfig {

	@Autowired
	public RegionEntityConverter regionEntityConverter;

	@Bean
	public ConverterMapping regionEntityConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(regionEntityConverter);
		mapping.setTypeCode(Region.class.getSimpleName());

		return mapping;
	}

}
