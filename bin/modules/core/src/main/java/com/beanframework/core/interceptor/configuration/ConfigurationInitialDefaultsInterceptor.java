package com.beanframework.core.interceptor.configuration;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Configuration> {

	@Override
	public void onInitialDefaults(Configuration model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
