package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotListLoadInterceptor;

@Configuration
public class DynamicFieldSlotListInterceptorConfig {

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public DynamicFieldSlotListLoadInterceptor dynamicFieldSlotListLoadInterceptor() {
		return new DynamicFieldSlotListLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotListLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotListLoadInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName() + ModelService.DEFAULT_LIST_LOAD_INTERCEPTOR_POSTFIX);

		return mapping;
	}
}
