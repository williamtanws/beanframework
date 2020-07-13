package com.beanframework.core.interceptor.language;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Language> {

	@Override
	public void onInitialDefaults(Language model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
