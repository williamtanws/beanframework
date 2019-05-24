package com.beanframework.imex.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;
import com.beanframework.imex.domain.Imex;

public class ImexLoadInterceptor extends AbstractLoadInterceptor<Imex> {

	@Override
	public void onLoad(Imex model, InterceptorContext context) throws InterceptorException {
	}

}
