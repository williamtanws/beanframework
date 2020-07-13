package com.beanframework.core.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.template.domain.Template;

public class TemplateRemoveInterceptor extends AbstractRemoveInterceptor<Template> {

	@Override
	public void onRemove(Template model, InterceptorContext context) throws InterceptorException {

	}

}
