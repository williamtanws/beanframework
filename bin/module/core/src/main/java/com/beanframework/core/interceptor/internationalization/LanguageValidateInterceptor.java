package com.beanframework.core.interceptor.internationalization;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.internationalization.domain.Language;

public class LanguageValidateInterceptor extends AbstractValidateInterceptor<Language> {

	@Override
	public void onValidate(Language model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
