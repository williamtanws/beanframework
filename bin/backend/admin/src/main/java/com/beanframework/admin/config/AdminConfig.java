package com.beanframework.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.admin.converter.DtoAdminConverter;
import com.beanframework.admin.converter.EntityAdminConverter;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.interceptor.AdminLoadInterceptor;
import com.beanframework.common.converter.ConverterMapping;
import com.beanframework.common.interceptor.InterceptorMapping;

@Configuration
public class AdminConfig {

	@Bean
	public DtoAdminConverter dtoAdminConverter() {
		return new DtoAdminConverter();
	}
	
	@Bean
	public ConverterMapping dtoAdminConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(dtoAdminConverter());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	} 
	
	@Bean
	public EntityAdminConverter entityAdminConverter() {
		return new EntityAdminConverter();
	}
	
	@Bean
	public ConverterMapping entityAdminConverterMapping() {
		ConverterMapping mapping = new ConverterMapping();
		mapping.setConverter(entityAdminConverter());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	} 

	@Bean
	public AdminLoadInterceptor adminLoadInterceptor() {
		return new AdminLoadInterceptor();
	}

	@Bean
	public InterceptorMapping adminLoadInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(adminLoadInterceptor());
		interceptorMapping.setTypeCode(Admin.class.getSimpleName());

		return interceptorMapping;
	}
}
