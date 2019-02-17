package com.beanframework.language.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitialDefaultsInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageInitialDefaultsInterceptor implements InitialDefaultsInterceptor<Language> {

	@Override
	public void onInitialDefaults(Language model, InterceptorContext context) throws InterceptorException {
	}

}
