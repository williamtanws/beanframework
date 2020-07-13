package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.template.domain.Template;

public class TemplatePrepareInterceptor extends AbstractPrepareInterceptor<Template> {

	@Override
	public void onPrepare(Template model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}
}
