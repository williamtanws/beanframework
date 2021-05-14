package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.customer.CustomerInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.customer.CustomerLoadInterceptor;
import com.beanframework.core.interceptor.customer.CustomerPrepareInterceptor;
import com.beanframework.core.interceptor.customer.CustomerRemoveInterceptor;
import com.beanframework.core.interceptor.customer.CustomerValidateInterceptor;
import com.beanframework.user.domain.Customer;

@Configuration
public class CustomerInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public CustomerInitialDefaultsInterceptor customerInitialDefaultsInterceptor() {
    return new CustomerInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping customerInitialDefaultsInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(customerInitialDefaultsInterceptor());
    mapping.setTypeCode(Customer.class.getSimpleName());

    return mapping;
  }

  //////////////////////
  // Load Interceptor //
  //////////////////////

  @Bean
  public CustomerLoadInterceptor customerLoadInterceptor() {
    return new CustomerLoadInterceptor();
  }

  @Bean
  public InterceptorMapping customerLoadInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(customerLoadInterceptor());
    mapping.setTypeCode(Customer.class.getSimpleName());

    return mapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public CustomerPrepareInterceptor customerPrepareInterceptor() {
    return new CustomerPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping customerPrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(customerPrepareInterceptor());
    mapping.setTypeCode(Customer.class.getSimpleName());

    return mapping;
  }

  //////////////////////////
  // Validate Interceptor //
  //////////////////////////

  @Bean
  public CustomerValidateInterceptor customerValidateInterceptor() {
    return new CustomerValidateInterceptor();
  }

  @Bean
  public InterceptorMapping customerValidateInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(customerValidateInterceptor());
    mapping.setTypeCode(Customer.class.getSimpleName());

    return mapping;
  }

  ////////////////////////
  // Remove Interceptor //
  ////////////////////////

  @Bean
  public CustomerRemoveInterceptor customerRemoveInterceptor() {
    return new CustomerRemoveInterceptor();
  }

  @Bean
  public InterceptorMapping customerRemoveInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(customerRemoveInterceptor());
    mapping.setTypeCode(Customer.class.getSimpleName());

    return mapping;
  }
}
