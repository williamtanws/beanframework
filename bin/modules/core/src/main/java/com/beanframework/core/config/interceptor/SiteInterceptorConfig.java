package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.cms.domain.Site;
import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.cms.site.SiteInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.cms.site.SiteLoadInterceptor;
import com.beanframework.core.interceptor.cms.site.SitePrepareInterceptor;
import com.beanframework.core.interceptor.cms.site.SiteRemoveInterceptor;
import com.beanframework.core.interceptor.cms.site.SiteValidateInterceptor;

@Configuration
public class SiteInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public SiteInitialDefaultsInterceptor siteInitialDefaultsInterceptor() {
		return new SiteInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping siteInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(siteInitialDefaultsInterceptor());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public SiteLoadInterceptor siteLoadInterceptor() {
		return new SiteLoadInterceptor();
	}

	@Bean
	public InterceptorMapping siteLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(siteLoadInterceptor());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public SitePrepareInterceptor sitePrepareInterceptor() {
		return new SitePrepareInterceptor();
	}

	@Bean
	public InterceptorMapping sitePrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(sitePrepareInterceptor());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public SiteValidateInterceptor siteValidateInterceptor() {
		return new SiteValidateInterceptor();
	}

	@Bean
	public InterceptorMapping siteValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(siteValidateInterceptor());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public SiteRemoveInterceptor siteRemoveInterceptor() {
		return new SiteRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping siteRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(siteRemoveInterceptor());
		mapping.setTypeCode(Site.class.getSimpleName());

		return mapping;
	}
}
