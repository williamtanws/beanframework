package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.dynamicfieldslot.DynamicFieldSlotRemoveInterceptor;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Configuration
public class DynamicFieldSlotInterceptorConfig {

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public DynamicFieldSlotRemoveInterceptor dynamicFieldSlotRemoveInterceptor() {
    return new DynamicFieldSlotRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping dynamicFieldSlotRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(dynamicFieldSlotRemoveInterceptor());
    mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

    return mapping;
  }
}
