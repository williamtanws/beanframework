package com.beanframework.language.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.LoadInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageLoadInterceptor implements LoadInterceptor<Language> {

	@Override
	public void onLoad(Language model) throws InterceptorException {
	}

}
