package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.enumuration.EnumerationRemoveInterceptor;
import com.beanframework.enumuration.domain.Enumeration;

@Configuration
public class EnumerationInterceptorConfig {

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public EnumerationRemoveInterceptor enumerationRemoveInterceptor() {
    return new EnumerationRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping enumerationRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(enumerationRemoveInterceptor());
    mapping.setTypeCode(Enumeration.class.getSimpleName());

    return mapping;
  }
}
