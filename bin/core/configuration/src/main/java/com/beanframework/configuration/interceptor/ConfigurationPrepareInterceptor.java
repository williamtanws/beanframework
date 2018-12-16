package com.beanframework.configuration.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationPrepareInterceptor implements PrepareInterceptor<Configuration> {

	@Override
	public void onPrepare(Configuration model) throws InterceptorException {
		
	}

}
