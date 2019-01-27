package com.beanframework.language.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.InitializeInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageInitializeInterceptor implements InitializeInterceptor<Language> {

	@Override
	public void onInitialize(Language model) throws InterceptorException {
	}

}
