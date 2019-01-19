package com.beanframework.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.interceptor.AdminInitialDefaultsInterceptor;
import com.beanframework.admin.interceptor.AdminInitializeInterceptor;
import com.beanframework.admin.interceptor.AdminLoadInterceptor;
import com.beanframework.admin.interceptor.AdminPrepareInterceptor;
import com.beanframework.admin.interceptor.AdminRemoveInterceptor;
import com.beanframework.admin.interceptor.AdminValidateInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;

@Configuration
public class AdminInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public AdminInitialDefaultsInterceptor adminInitialDefaultsInterceptor() {
		return new AdminInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping adminInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(adminInitialDefaultsInterceptor());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public AdminInitializeInterceptor adminInitializeInterceptor() {
		return new AdminInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping adminInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(adminInitializeInterceptor());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public AdminLoadInterceptor adminLoadInterceptor() {
		return new AdminLoadInterceptor();
	}

	@Bean
	public InterceptorMapping adminLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(adminLoadInterceptor());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public AdminPrepareInterceptor adminPrepareInterceptor() {
		return new AdminPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping adminPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(adminPrepareInterceptor());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public AdminValidateInterceptor adminValidateInterceptor() {
		return new AdminValidateInterceptor();
	}

	@Bean
	public InterceptorMapping adminValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(adminValidateInterceptor());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public AdminRemoveInterceptor adminRemoveInterceptor() {
		return new AdminRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping adminRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(adminRemoveInterceptor());
		mapping.setTypeCode(Admin.class.getSimpleName());

		return mapping;
	}
}
