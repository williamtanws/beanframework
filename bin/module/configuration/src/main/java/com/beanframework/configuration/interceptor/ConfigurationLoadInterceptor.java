package com.beanframework.configuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationLoadInterceptor implements LoadInterceptor<Configuration> {

	@Override
	public void onLoad(Configuration model, InterceptorContext context) throws InterceptorException {
	}

}
