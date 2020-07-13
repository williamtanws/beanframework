package com.beanframework.core.interceptor.language;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageRemoveInterceptor extends AbstractRemoveInterceptor<Language> {

	@Override
	public void onRemove(Language model, InterceptorContext context) throws InterceptorException {
	}

}
