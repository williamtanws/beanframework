package com.beanframework.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.admin.converter.DtoAdminConverter;
import com.beanframework.admin.converter.EntityAdminConverter;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.interceptor.AdminInitialDefaultsInterceptor;
import com.beanframework.admin.interceptor.AdminLoadInterceptor;
import com.beanframework.admin.interceptor.AdminPrepareInterceptor;
import com.beanframework.admin.interceptor.AdminRemoveInterceptor;
import com.beanframework.admin.interceptor.AdminValidateInterceptor;
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
	public AdminInitialDefaultsInterceptor adminInitialDefaultsInterceptor() {
		return new AdminInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping adminInitialDefaultsInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(adminInitialDefaultsInterceptor());
		interceptorMapping.setTypeCode(Admin.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public AdminValidateInterceptor adminValidateInterceptor() {
		return new AdminValidateInterceptor();
	}

	@Bean
	public InterceptorMapping adminValidateInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(adminValidateInterceptor());
		interceptorMapping.setTypeCode(Admin.class.getSimpleName());

		return interceptorMapping;
	}
	
	@Bean
	public AdminPrepareInterceptor adminPrepareInterceptor() {
		return new AdminPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping adminPrepareInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(adminPrepareInterceptor());
		interceptorMapping.setTypeCode(Admin.class.getSimpleName());

		return interceptorMapping;
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
	
	@Bean
	public AdminRemoveInterceptor adminRemoveInterceptor() {
		return new AdminRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping adminRemoveInterceptorMapping() {
		InterceptorMapping interceptorMapping = new InterceptorMapping();
		interceptorMapping.setInterceptor(adminRemoveInterceptor());
		interceptorMapping.setTypeCode(Admin.class.getSimpleName());
		
		return interceptorMapping;
	}
		
}
