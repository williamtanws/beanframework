package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.internationalization.LanguageRemoveInterceptor;
import com.beanframework.internationalization.domain.Language;

@Configuration
public class LanguageInterceptorConfig {

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public LanguageRemoveInterceptor languageRemoveInterceptor() {
    return new LanguageRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping languageRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(languageRemoveInterceptor());
    mapping.setTypeCode(Language.class.getSimpleName());

    return mapping;
  }
}
