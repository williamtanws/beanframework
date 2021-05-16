package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.email.EmailInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.email.EmailPrepareInterceptor;
import com.beanframework.core.interceptor.email.EmailValidateInterceptor;
import com.beanframework.email.domain.Email;

@Configuration
public class EmailInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public EmailInitialDefaultsInterceptor emailInitialDefaultsInterceptor() {
    return new EmailInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping emailInitialDefaultsInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(emailInitialDefaultsInterceptor());
    mapping.setTypeCode(Email.class.getSimpleName());

    return mapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public EmailPrepareInterceptor emailPrepareInterceptor() {
    return new EmailPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping emailPrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(emailPrepareInterceptor());
    mapping.setTypeCode(Email.class.getSimpleName());

    return mapping;
  }

  //////////////////////////
  // Validate Interceptor //
  //////////////////////////

  @Bean
  public EmailValidateInterceptor emailValidateInterceptor() {
    return new EmailValidateInterceptor();
  }

  @Bean
  public InterceptorMapping emailValidateInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(emailValidateInterceptor());
    mapping.setTypeCode(Email.class.getSimpleName());

    return mapping;
  }
}
