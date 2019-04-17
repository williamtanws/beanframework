package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.interceptor.CronjobDataListLoadInterceptor;
import com.beanframework.cronjob.interceptor.CronjobListLoadInterceptor;

@Configuration
public class CronjobListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public CronjobListLoadInterceptor cronjobListLoadInterceptor() {
		return new CronjobListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobListLoadInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName() + "List");

		return mapping;
	}
	
	@Bean
	public CronjobDataListLoadInterceptor cronjobDataListLoadInterceptor() {
		return new CronjobDataListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobDataListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobDataListLoadInterceptor());
		mapping.setTypeCode(CronjobData.class.getSimpleName() + "List");

		return mapping;
	}
}
