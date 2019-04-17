package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.vendor.domain.Vendor;
import com.beanframework.vendor.interceptor.VendorListLoadInterceptor;

@Configuration
public class VendorListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public VendorListLoadInterceptor vendorListLoadInterceptor() {
		return new VendorListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping vendorListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(vendorListLoadInterceptor());
		mapping.setTypeCode(Vendor.class.getSimpleName() + "List");

		return mapping;
	}
}
