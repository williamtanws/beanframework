package com.beanframework.configuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationInitializeInterceptor implements InitializeInterceptor<Configuration> {

	@Override
	public void onInitialize(Configuration model, InterceptorContext context) throws InterceptorException {
	}

}
