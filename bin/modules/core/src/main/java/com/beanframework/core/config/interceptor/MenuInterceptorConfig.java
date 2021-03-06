package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.menu.MenuInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.menu.MenuLoadInterceptor;
import com.beanframework.core.interceptor.menu.MenuPrepareInterceptor;
import com.beanframework.menu.domain.Menu;

@Configuration
public class MenuInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public MenuInitialDefaultsInterceptor menuInitialDefaultsInterceptor() {
    return new MenuInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping menuInitialDefaultsInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(menuInitialDefaultsInterceptor());
    interceptorMapping.setTypeCode(Menu.class.getSimpleName());

    return interceptorMapping;
  }

  //////////////////////
  // Load Interceptor //
  //////////////////////

  @Bean
  public MenuLoadInterceptor menuLoadInterceptor() {
    return new MenuLoadInterceptor();
  }

  @Bean
  public InterceptorMapping menuLoadInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(menuLoadInterceptor());
    interceptorMapping.setTypeCode(Menu.class.getSimpleName());

    return interceptorMapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public MenuPrepareInterceptor menuPrepareInterceptor() {
    return new MenuPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping menuPrepareInterceptorMapping() {
    InterceptorMapping interceptorMapping = new InterceptorMapping();
    interceptorMapping.setInterceptor(menuPrepareInterceptor());
    interceptorMapping.setTypeCode(Menu.class.getSimpleName());

    return interceptorMapping;
  }
}
