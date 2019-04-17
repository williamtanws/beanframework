package com.beanframework.configuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationLoadInterceptor extends AbstractLoadInterceptor<Configuration> {

	@Override
	public Configuration onLoad(Configuration model, InterceptorContext context) throws InterceptorException {

		Configuration prototype = new Configuration();
		loadCommonProperties(model, prototype, context);
		prototype.setValue(model.getValue());

		return prototype;
	}

}
