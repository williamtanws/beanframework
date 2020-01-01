package com.beanframework.template.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.template.domain.Template;

public class TemplateLoadInterceptor extends AbstractLoadInterceptor<Template> {

	@Override
	public void onLoad(Template model, InterceptorContext context) throws InterceptorException {
	}

}
