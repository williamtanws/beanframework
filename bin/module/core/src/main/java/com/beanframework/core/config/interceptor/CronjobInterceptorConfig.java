package com.beanframework.core.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.core.interceptor.cronjob.CronjobDataInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobDataLoadInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobDataPrepareInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobDataRemoveInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobDataValidateInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobInitialDefaultsInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobLoadInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobPrepareInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobRemoveInterceptor;
import com.beanframework.core.interceptor.cronjob.CronjobValidateInterceptor;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;

@Configuration
public class CronjobInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public CronjobInitialDefaultsInterceptor cronjobInitialDefaultsInterceptor() {
		return new CronjobInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobInitialDefaultsInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CronjobDataInitialDefaultsInterceptor cronjobDataInitialDefaultsInterceptor() {
		return new CronjobDataInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping cronjoDatabInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobDataInitialDefaultsInterceptor());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public CronjobLoadInterceptor cronjobLoadInterceptor() {
		return new CronjobLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobLoadInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CronjobDataLoadInterceptor cronjobDataLoadInterceptor() {
		return new CronjobDataLoadInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobDataLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobDataLoadInterceptor());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public CronjobPrepareInterceptor cronjobPrepareInterceptor() {
		return new CronjobPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobPrepareInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CronjobDataPrepareInterceptor cronjobDataPrepareInterceptor() {
		return new CronjobDataPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobDataPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobDataPrepareInterceptor());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public CronjobValidateInterceptor cronjobValidateInterceptor() {
		return new CronjobValidateInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobValidateInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}

	@Bean
	public CronjobDataValidateInterceptor cronjobDataValidateInterceptor() {
		return new CronjobDataValidateInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobDataValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobDataValidateInterceptor());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public CronjobRemoveInterceptor cronjobRemoveInterceptor() {
		return new CronjobRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobRemoveInterceptor());
		mapping.setTypeCode(Cronjob.class.getSimpleName());

		return mapping;
	}
	
	@Bean
	public CronjobDataRemoveInterceptor cronjobDataRemoveInterceptor() {
		return new CronjobDataRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping cronjobDataRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(cronjobDataRemoveInterceptor());
		mapping.setTypeCode(CronjobData.class.getSimpleName());

		return mapping;
	}
}
