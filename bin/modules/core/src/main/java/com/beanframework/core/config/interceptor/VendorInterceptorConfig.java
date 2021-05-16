package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.vendor.VendorInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.vendor.VendorLoadInterceptor;
import com.beanframework.core.interceptor.vendor.VendorPrepareInterceptor;
import com.beanframework.user.domain.Vendor;

@Configuration
public class VendorInterceptorConfig {

  //////////////////////////////////
  // Initial Defaults Interceptor //
  //////////////////////////////////

  @Bean
  public VendorInitialDefaultsInterceptor vendorInitialDefaultsInterceptor() {
    return new VendorInitialDefaultsInterceptor();
  }

  @Bean
  public InterceptorMapping vendorInitialDefaultsInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(vendorInitialDefaultsInterceptor());
    mapping.setTypeCode(Vendor.class.getSimpleName());

    return mapping;
  }

  //////////////////////
  // Load Interceptor //
  //////////////////////

  @Bean
  public VendorLoadInterceptor vendorLoadInterceptor() {
    return new VendorLoadInterceptor();
  }

  @Bean
  public InterceptorMapping vendorLoadInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(vendorLoadInterceptor());
    mapping.setTypeCode(Vendor.class.getSimpleName());

    return mapping;
  }

  /////////////////////////
  // Prepare Interceptor //
  /////////////////////////

  @Bean
  public VendorPrepareInterceptor vendorPrepareInterceptor() {
    return new VendorPrepareInterceptor();
  }

  @Bean
  public InterceptorMapping vendorPrepareInterceptorMapping() {
    InterceptorMapping mapping = new InterceptorMapping();
    mapping.setInterceptor(vendorPrepareInterceptor());
    mapping.setTypeCode(Vendor.class.getSimpleName());

    return mapping;
  }
}
