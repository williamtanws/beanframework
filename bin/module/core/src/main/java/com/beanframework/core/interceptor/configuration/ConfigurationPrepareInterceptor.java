package com.beanframework.core.interceptor.configuration;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationPrepareInterceptor extends AbstractPrepareInterceptor<Configuration> {

	@Override
	public void onPrepare(Configuration model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

	}

}
