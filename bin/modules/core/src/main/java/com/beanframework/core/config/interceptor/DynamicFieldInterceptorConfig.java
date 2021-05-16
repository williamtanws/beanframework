package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.dynamicfield.DynamicFieldRemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicField;

@Configuration
public class DynamicFieldInterceptorConfig {

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public DynamicFieldRemoveInterceptor dynamicFieldRemoveInterceptor() {
    return new DynamicFieldRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldRemoveInterceptor());
    mapping.setTypeCode(DynamicField.class.getSimpleName());

    return mapping;
  }
}
