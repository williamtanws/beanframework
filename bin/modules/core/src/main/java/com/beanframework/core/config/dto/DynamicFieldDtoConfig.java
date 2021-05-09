package com.beanframework.core.config.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DynamicFieldDtoConverter;
import com.beanframework.core.converter.dto.DynamicFieldSlotDtoConverter;
import com.beanframework.core.converter.dto.DynamicFieldTemplateDtoConverter;
import com.beanframework.core.converter.populator.DynamicFieldPopulator;
import com.beanframework.core.converter.populator.DynamicFieldSlotPopulator;
import com.beanframework.core.converter.populator.DynamicFieldTemplatePopulator;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.DynamicFieldTemplateDto;

@Configuration
public class DynamicFieldDtoConfig {
	
	@Autowired
	public DynamicFieldPopulator dynamicFieldPopulator;
	
	@Autowired
	public DynamicFieldSlotPopulator dynamicFieldSlotPopulator;
	
	@Autowired
	public DynamicFieldTemplatePopulator dynamicFieldTemplatePopulator;

	@Bean
	public DynamicFieldDtoConverter dynamicFieldDtoConverter() {
		DynamicFieldDtoConverter converter = new DynamicFieldDtoConverter();
		converter.addPopulator(dynamicFieldPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping dynamicFieldDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dynamicFieldDtoConverter());
		mapping.setTypeCode(DynamicFieldDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DynamicFieldSlotDtoConverter dynamicFieldSlotDtoConverter() {
		DynamicFieldSlotDtoConverter converter = new DynamicFieldSlotDtoConverter();
		converter.addPopulator(dynamicFieldSlotPopulator);
		return converter;
	}

	@Bean
	public ConverterMapping dynamicFieldSlotDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dynamicFieldSlotDtoConverter());
		mapping.setTypeCode(DynamicFieldSlotDto.class.getSimpleName());

		return mapping;
	}

	@Bean
	public DynamicFieldTemplateDtoConverter dynamicFieldTemplateDtoConverter() {
		DynamicFieldTemplateDtoConverter converter = new DynamicFieldTemplateDtoConverter();
		converter.addPopulator(dynamicFieldTemplatePopulator);
		return converter;
	}

	@Bean
	public ConverterMapping dynamicFieldTemplateDtoConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dynamicFieldTemplateDtoConverter());
		mapping.setTypeCode(DynamicFieldTemplateDto.class.getSimpleName());

		return mapping;
	}
}
