package com.beanframework.configuration.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.configuration.domain.Configuration;

public class ConfigurationValidateInterceptor extends AbstractValidateInterceptor<Configuration> {

	@Override
	public void onValidate(Configuration model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}

}
