package com.beanframework.core.config.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.dto.DtoDynamicFieldConverter;
import com.beanframework.core.converter.dto.DtoDynamicFieldSlotConverter;
import com.beanframework.core.converter.dto.DtoDynamicFieldTemplateConverter;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldSlotDto;
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
	public DtoDynamicFieldSlotConverter dtoDynamicFieldSlotConverter() {
		return new DtoDynamicFieldSlotConverter();
	}

	@Bean
	public ConverterMapping dtoDynamicFieldSlotConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoDynamicFieldSlotConverter());
		mapping.setTypeCode(DynamicFieldSlotDto.class.getSimpleName());

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
