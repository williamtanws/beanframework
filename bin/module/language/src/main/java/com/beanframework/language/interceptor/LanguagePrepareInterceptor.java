package com.beanframework.language.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.language.domain.Language;

public class LanguagePrepareInterceptor implements PrepareInterceptor<Language> {

	@Override
	public void onPrepare(Language model, InterceptorContext context) throws InterceptorException {
	}

}
