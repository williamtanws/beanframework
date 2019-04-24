package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.interceptor.SiteListLoadInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;

@Configuration
public class SiteListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public SiteListLoadInterceptor siteListLoadInterceptor() {
		return new SiteListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping siteListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(siteListLoadInterceptor());
		mapping.setTypeCode(Site.class.getSimpleName()+ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
