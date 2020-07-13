package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.template.domain.Template;

public class TemplateValidateInterceptor extends AbstractValidateInterceptor<Template> {

	@Override
	public void onValidate(Template model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);
	}
}
