package com.beanframework.configuration.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationRemoveInterceptor implements RemoveInterceptor<Configuration> {

	@Override
	public void onRemove(Configuration model) throws InterceptorException {
	}

}
