package com.beanframework.core.interceptor.imex;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractInitialDefaultsInterceptor;
import com.beanframework.imex.domain.Imex;

public class ImexInitialDefaultsInterceptor extends AbstractInitialDefaultsInterceptor<Imex> {

	@Override
	public void onInitialDefaults(Imex model, InterceptorContext context) throws InterceptorException {
		super.onInitialDefaults(model, context);
	}

}
