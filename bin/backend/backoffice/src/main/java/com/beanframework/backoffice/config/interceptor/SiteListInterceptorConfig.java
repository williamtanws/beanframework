package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Site;
import com.beanframework.cms.interceptor.SiteListLoadInterceptor;
import com.beanframework.common.interceptor.InterceptorMapping;

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
		mapping.setTypeCode(Site.class.getSimpleName()+"List");

		return mapping;
	}
}
