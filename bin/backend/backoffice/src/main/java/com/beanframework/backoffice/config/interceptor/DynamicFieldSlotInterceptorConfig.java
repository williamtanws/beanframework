package com.beanframework.backoffice.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beanframework.common.interceptor.InterceptorMapping;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotInitialDefaultsInterceptor;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotInitializeInterceptor;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotLoadInterceptor;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotPrepareInterceptor;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotRemoveInterceptor;
import com.beanframework.dynamicfield.interceptor.slot.DynamicFieldSlotValidateInterceptor;

@Configuration
public class DynamicFieldSlotInterceptorConfig {

	//////////////////////////////////
	// Initial Defaults Interceptor //
	//////////////////////////////////

	@Bean
	public DynamicFieldSlotInitialDefaultsInterceptor dynamicFieldSlotInitialDefaultsInterceptor() {
		return new DynamicFieldSlotInitialDefaultsInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotInitialDefaultsInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotInitialDefaultsInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}

	////////////////////////////
	// Initialize Interceptor //
	////////////////////////////

	@Bean
	public DynamicFieldSlotInitializeInterceptor dynamicFieldSlotInitializeInterceptor() {
		return new DynamicFieldSlotInitializeInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotInitializeInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotInitializeInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}

	//////////////////////
	// Load Interceptor //
	//////////////////////

	@Bean
	public DynamicFieldSlotLoadInterceptor dynamicFieldSlotLoadInterceptor() {
		return new DynamicFieldSlotLoadInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotLoadInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotLoadInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}

	/////////////////////////
	// Prepare Interceptor //
	/////////////////////////

	@Bean
	public DynamicFieldSlotPrepareInterceptor dynamicFieldSlotPrepareInterceptor() {
		return new DynamicFieldSlotPrepareInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotPrepareInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotPrepareInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}

	//////////////////////////
	// Validate Interceptor //
	//////////////////////////

	@Bean
	public DynamicFieldSlotValidateInterceptor dynamicFieldSlotValidateInterceptor() {
		return new DynamicFieldSlotValidateInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotValidateInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotValidateInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}

	////////////////////////
	// Remove Interceptor //
	////////////////////////

	@Bean
	public DynamicFieldSlotRemoveInterceptor dynamicFieldSlotRemoveInterceptor() {
		return new DynamicFieldSlotRemoveInterceptor();
	}

	@Bean
	public InterceptorMapping dynamicFieldSlotRemoveInterceptorMapping() {
		InterceptorMapping mapping = new InterceptorMapping();
		mapping.setInterceptor(dynamicFieldSlotRemoveInterceptor());
		mapping.setTypeCode(DynamicFieldSlot.class.getSimpleName());

		return mapping;
	}
}
