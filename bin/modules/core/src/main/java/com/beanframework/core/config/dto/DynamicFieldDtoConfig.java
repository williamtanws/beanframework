package com.beanframework.core.config.dto;

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
	
	@Bean
	public DynamicFieldPopulator dynamicFieldPopulator() {
		return new DynamicFieldPopulator();
	}

	@Bean
	public DynamicFieldDtoConverter dtoDynamicFieldConverter() {
		DynamicFieldDtoConverter converter = new DynamicFieldDtoConverter();
		converter.addPopulator(dynamicFieldPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoDynamicFieldConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldConverter());
		mapping.setTypeCode(DynamicFieldDto.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public DynamicFieldSlotPopulator dynamicFieldSlotPopulator() {
		return new DynamicFieldSlotPopulator();
	}

	@Bean
	public DynamicFieldSlotDtoConverter dtoDynamicFieldSlotConverter() {
		DynamicFieldSlotDtoConverter converter = new DynamicFieldSlotDtoConverter();
		converter.addPopulator(dynamicFieldSlotPopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoDynamicFieldSlotConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldSlotConverter());
		mapping.setTypeCode(DynamicFieldSlotDto.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public DynamicFieldTemplatePopulator dynamicFieldTemplatePopulator() {
		return new DynamicFieldTemplatePopulator();
	}

	@Bean
	public DynamicFieldTemplateDtoConverter dtoDynamicFieldTemplateConverter() {
		DynamicFieldTemplateDtoConverter converter = new DynamicFieldTemplateDtoConverter();
		converter.addPopulator(dynamicFieldTemplatePopulator());
		return converter;
	}

	@Bean
	public ConverterMapping dtoDynamicFieldTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldTemplateConverter());
		mapping.setTypeCode(DynamicFieldTemplateDto.class.getSimpleName());

		return mapping;
	}
}
