package com.beanframework.core.interceptor.internationalization;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.internationalization.domain.Language;

public class LanguagePrepareInterceptor extends AbstractPrepareInterceptor<Language> {

	@Override
	public void onPrepare(Language model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);
	}

}
