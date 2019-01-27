package com.beanframework.configuration.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Configuration> {

	@Override
	public void onInitialDefaults(Configuration model) throws InterceptorException {
	}

}
