package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.interceptor.CustomerListLoadInterceptor;

@Configuration
public class CustomerListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public CustomerListLoadInterceptor customerListLoadInterceptor() {
		return new CustomerListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping customerListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(customerListLoadInterceptor());
		mapping.setTypeCode(Customer.class.getSimpleName() + "List");

		return mapping;
	}
}
