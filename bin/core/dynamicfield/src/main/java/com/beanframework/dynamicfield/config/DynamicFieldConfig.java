package com.beanframework.dynamicfield.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.dynamicfield.converter.DtoDynamicFieldConverter;
import com.beanframework.dynamicfield.converter.EntityDynamicFieldConverter;
import com.beanframework.dynamicfield.domain.DynamicField;

@Configuration
public class DynamicFieldConfig {

	@Bean
	public DtoDynamicFieldConverter dtoDynamicFieldConverter() {
		return new DtoDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldConverter());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public EntityDynamicFieldConverter entityDynamicFieldConverter() {
		return new EntityDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping entityDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityDynamicFieldConverter());
		mapping.setTypeCode(DynamicField.class.getSimpleName());

		return mapping;
	}
}
