package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.dynamicfieldtemplate.DynamicFieldTemplateDynamicFieldSlotRemoveInterceptor;
import com.beanframework.core.interceptor.dynamicfieldtemplate.DynamicFieldTemplateInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.dynamicfieldtemplate.DynamicFieldTemplateLoadInterceptor;
import com.beanframework.core.interceptor.dynamicfieldtemplate.DynamicFieldTemplatePrepareInterceptor;
import com.beanframework.core.interceptor.dynamicfieldtemplate.DynamicFieldTemplateRemoveInterceptor;
import com.beanframework.core.interceptor.dynamicfieldtemplate.DynamicFieldTemplateValidateInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Configuration
public class DynamicFieldTemplateInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public DynamicFieldTemplateInitialDefaultsInterceptor dynamicFieldTemplateInitialDefaultsInterceptor() {
    return new DynamicFieldTemplateInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldTemplateInitialDefaultsInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldTemplateInitialDefaultsInterceptor());
    mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

    return mapping;
  }

  //////////////////////
  // Load Interceptor //
  //////////////////////

  @Bean
  public DynamicFieldTemplateLoadInterceptor dynamicFieldTemplateLoadInterceptor() {
    return new DynamicFieldTemplateLoadInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldTemplateLoadInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldTemplateLoadInterceptor());
    mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

    return mapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public DynamicFieldTemplatePrepareInterceptor dynamicFieldTemplatePrepareInterceptor() {
    return new DynamicFieldTemplatePrepareInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldTemplatePrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldTemplatePrepareInterceptor());
    mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

    return mapping;
  }

  //////////////////////////
  // Validate Interceptor //
  //////////////////////////

  @Bean
  public DynamicFieldTemplateValidateInterceptor dynamicFieldTemplateValidateInterceptor() {
    return new DynamicFieldTemplateValidateInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldTemplateValidateInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldTemplateValidateInterceptor());
    mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

    return mapping;
  }

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public DynamicFieldTemplateRemoveInterceptor dynamicFieldTemplateRemoveInterceptor() {
    return new DynamicFieldTemplateRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldTemplateRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldTemplateRemoveInterceptor());
    mapping.setTypeCode(DynamicFieldTemplate.class.getSimpleName());

    return mapping;
  }

  //////////////////////////////////
  // DynamicFieldSlot Interceptor //
  //////////////////////////////////

  @Bean
  public DynamicFieldTemplateDynamicFieldSlotRemoveInterceptor dynamicFieldTemplateDynamicFieldSlotRemoveInterceptor() {
    return new DynamicFieldTemplateDynamicFieldSlotRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldTemplateDynamicFieldSlotRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldTemplateDynamicFieldSlotRemoveInterceptor());
    mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());
    mapping.setOrder(Integer.MIN_VALUE);

    return mapping;
  }
}
