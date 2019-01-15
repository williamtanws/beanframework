package com.beanframework.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.interceptor.AdminInitialDefaultsInterceptor;
import com.beanframework.admin.interceptor.AdminLoadInterceptor;
import com.beanframework.admin.interceptor.AdminPrepareInterceptor;
import com.beanframework.admin.interceptor.AdminRemoveInterceptor;
import com.beanframework.admin.interceptor.AdminValidateInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;

@Configuration
public class AdminInterceptorConfig {
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
