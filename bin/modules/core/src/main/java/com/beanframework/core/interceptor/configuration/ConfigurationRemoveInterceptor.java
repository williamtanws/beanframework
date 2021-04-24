package com.beanframework.core.interceptor.configuration;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationRemoveInterceptor extends AbstractRemoveInterceptor<Configuration> {

	@Override
	public void onRemove(Configuration model, InterceptorContext context) throws InterceptorException {
	}

}
