package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.LogentryInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.LogentryLoadInterceptor;
import com.beanframework.core.interceptor.LogentryPrepareInterceptor;
import com.beanframework.core.interceptor.LogentryRemoveInterceptor;
import com.beanframework.core.interceptor.LogentryValidateInterceptor;
import com.beanframework.logentry.domain.Logentry;

@Configuration
public class LogentryInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public LogentryInitialDefaultsInterceptor logentryInitialDefaultsInterceptor() {
    return new LogentryInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping logentryInitialDefaultsInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(logentryInitialDefaultsInterceptor());
    mapping.setTypeCode(Logentry.class.getSimpleName());

    return mapping;
  }

  //////////////////////
  // Load Interceptor //
  //////////////////////

  @Bean
  public LogentryLoadInterceptor logentryLoadInterceptor() {
    return new LogentryLoadInterceptor();
  }

  @Bean
  public InterceptorMapping logentryLoadInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(logentryLoadInterceptor());
    mapping.setTypeCode(Logentry.class.getSimpleName());

    return mapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public LogentryPrepareInterceptor logentryPrepareInterceptor() {
    return new LogentryPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping logentryPrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(logentryPrepareInterceptor());
    mapping.setTypeCode(Logentry.class.getSimpleName());

    return mapping;
  }

  //////////////////////////
  // Validate Interceptor //
  //////////////////////////

  @Bean
  public LogentryValidateInterceptor logentryValidateInterceptor() {
    return new LogentryValidateInterceptor();
  }

  @Bean
  public InterceptorMapping logentryValidateInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(logentryValidateInterceptor());
    mapping.setTypeCode(Logentry.class.getSimpleName());

    return mapping;
  }

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public LogentryRemoveInterceptor logentryRemoveInterceptor() {
    return new LogentryRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping logentryRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(logentryRemoveInterceptor());
    mapping.setTypeCode(Logentry.class.getSimpleName());

    return mapping;
  }
}
