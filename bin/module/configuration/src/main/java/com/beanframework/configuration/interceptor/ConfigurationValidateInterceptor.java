package com.beanframework.configuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationValidateInterceptor implements ValidateInterceptor<Configuration> {

	@Override
	public void onValidate(Configuration model, InterceptorContext context) throws InterceptorException {

	}

}
