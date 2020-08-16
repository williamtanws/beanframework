package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.admin.AdminInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.admin.AdminLoadInterceptor;
import com.beanframework.core.interceptor.admin.AdminPrepareInterceptor;
import com.beanframework.core.interceptor.admin.AdminRemoveInterceptor;
import com.beanframework.core.interceptor.admin.AdminValidateInterceptor;
import com.beanframework.user.domain.Admin;

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
