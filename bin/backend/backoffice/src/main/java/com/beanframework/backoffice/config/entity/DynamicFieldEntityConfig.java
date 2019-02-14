package com.beanframework.backoffice.config.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.EntityDynamicFieldConverter;
import com.beanframework.core.converter.EntityDynamicFieldSlotConverter;
import com.beanframework.core.converter.EntityDynamicFieldTemplateConverter;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Configuration
public class DynamicFieldEntityConfig {

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
	
	@Bean
	public EntityDynamicFieldSlotConverter entityDynamicFieldSlotConverter() {
		return new EntityDynamicFieldSlotConverter();
	}

	@Bean
	public ConverterMapping entityDynamicFieldSlotConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityDynamicFieldSlotConverter());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}

	@Bean
	public EntityDynamicFieldTemplateConverter entityDynamicFieldTemplateConverter() {
		return new EntityDynamicFieldTemplateConverter();
	}

	@Bean
	public ConverterMapping entityDynamicFieldTemplateConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityDynamicFieldTemplateConverter());
		mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

		return mapping;
	}
}
