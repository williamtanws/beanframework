package com.beanframework.language.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageValidateInterceptor implements ValidateInterceptor<Language> {

	@Override
	public void onValidate(Language model) throws InterceptorException {

	}
}
