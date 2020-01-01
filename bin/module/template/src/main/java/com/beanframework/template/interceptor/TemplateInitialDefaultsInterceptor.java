package com.beanframework.template.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.template.domain.Template;

public class TemplateInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Template> {

	@Override
	public void onInitialDefaults(Template model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
