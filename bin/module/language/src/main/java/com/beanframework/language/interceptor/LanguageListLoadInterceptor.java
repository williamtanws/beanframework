package com.beanframework.language.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.language.domain.Language;

public class LanguageListLoadInterceptor extends AbstractLoadInterceptor<Language> {

	@Override
	public void onLoad(Language model, InterceptorContext context) throws InterceptorException {
		Language prototype = new Language();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());
		prototype.setSort(model.getSort());
		prototype.setActive(model.getActive());
		
		model = prototype;
	}

}
