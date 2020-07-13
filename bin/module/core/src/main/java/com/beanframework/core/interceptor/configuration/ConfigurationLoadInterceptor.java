package com.beanframework.core.interceptor.configuration;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationLoadInterceptor extends AbstractLoadInterceptor<Configuration> {

	@Override
	public void onLoad(Configuration model, InterceptorContext context) throws InterceptorException {
	}

}
