package com.beanframework.backoffice.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.DtoDynamicFieldConverter;
import com.beanframework.core.converter.DtoDynamicFieldTemplateConverter;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldTemplateDto;

@Configuration
public class DynamicFieldDtoConfig {
	
	@Bean
	public DtoDynamicFieldConverter dtoDynamicFieldConverter() {
		return new DtoDynamicFieldConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldConverter());
		mapping.setTypeCode(DynamicFieldDto.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public DtoDynamicFieldTemplateConverter dtoDynamicFieldTemplateConverter() {
		return new DtoDynamicFieldTemplateConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldTemplateConverter());
		mapping.setTypeCode(DynamicFieldTemplateDto.class.getSimpleName());

		return mapping;
	}
}