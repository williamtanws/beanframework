package com.beanframework.core.config.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.core.converter.entity.DynamicFieldEntityConverter;
import com.beanframework.core.converter.entity.DynamicFieldSlotEntityConverter;
import com.beanframework.core.converter.entity.DynamicFieldTemplateEntityConverter;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Configuration
public class DynamicFieldEntityConfig {

  @Autowired
  public DynamicFieldEntityConverter dynamicFieldEntityConverter;

  @Autowired
  public DynamicFieldSlotEntityConverter dynamicFieldSlotEntityConverter;

  @Autowired
  public DynamicFieldTemplateEntityConverter dynamicFieldTemplateEntityConverter;

  @Bean
  public ConverterMapping dynamicFieldEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(dynamicFieldEntityConverter);
    mapping.setTypeCode(DynamicField.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping dynamicFieldSlotEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(dynamicFieldSlotEntityConverter);
    mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

    return mapping;
  }

  @Bean
  public ConverterMapping dynamicFieldTemplateEntityConverterMapping() {
    ConverterMapping mapping = new ConverterMapping();
    mapping.setConverter(dynamicFieldTemplateEntityConverter);
    mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

    return mapping;
  }
}
