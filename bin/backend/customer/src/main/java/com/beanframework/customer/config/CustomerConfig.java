package com.beanframework.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.customer.converter.DtoCustomerConverter;
import com.beanframework.customer.converter.EntityCustomerConverter;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.interceptor.CustomerInitialDefaultsInterceptor;
import com.beanframework.customer.interceptor.CustomerLoadInterceptor;
import com.beanframework.customer.interceptor.CustomerPrepareInterceptor;
import com.beanframework.customer.interceptor.CustomerRemoveInterceptor;
import com.beanframework.customer.interceptor.CustomerValidateInterceptor;

@Configuration
public class CustomerConfig {

	@Bean
	public DtoCustomerConverter dtoCustomerConverter() {
		return new DtoCustomerConverter();
	}
	
	@Bean
	public ConverterMapping dtoCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoCustomerConverter());
		mapping.setTypeCode(Customer.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public EntityCustomerConverter entityCustomerConverter() {
		return new EntityCustomerConverter();
	}
	
	@Bean
	public ConverterMapping entityCustomerConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityCustomerConverter());
		mapping.setTypeCode(Customer.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public CustomerInitialDefaultsInterceptor customerInitialDefaultsInterceptor() {
		return new CustomerInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping customerInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(customerInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Customer.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public CustomerValidateInterceptor customerValidateInterceptor() {
		return new CustomerValidateInterceptor();
	}

	@Bean
	public InterceptorMapping customerValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(customerValidateInterceptor());
		interceptorMapping.setTypeCode(Customer.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public CustomerPrepareInterceptor customerPrepareInterceptor() {
		return new CustomerPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping customerPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(customerPrepareInterceptor());
		interceptorMapping.setTypeCode(Customer.class.getSimpleName());

		return interceptorMapping;
	}

	@Bean
	public CustomerLoadInterceptor customerLoadInterceptor() {
		return new CustomerLoadInterceptor();
	}

	@Bean
	public InterceptorMapping customerLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(customerLoadInterceptor());
		interceptorMapping.setTypeCode(Customer.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public CustomerRemoveInterceptor customerRemoveInterceptor() {
		return new CustomerRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping customerRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(customerRemoveInterceptor());
		interceptorMapping.setTypeCode(Customer.class.getSimpleName());
		
		return interceptorMapping;
	}
		
}
