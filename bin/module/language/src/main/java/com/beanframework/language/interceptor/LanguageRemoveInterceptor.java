package com.beanframework.language.interceptor;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.RemoveInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageRemoveInterceptor implements RemoveInterceptor<Language> {

	@Override
	public void onRemove(Language model) throws InterceptorException {
	}

}
