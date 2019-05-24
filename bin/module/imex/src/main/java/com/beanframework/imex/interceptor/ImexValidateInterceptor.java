package com.beanframework.imex.interceptor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;
import com.beanframework.imex.domain.Imex;

public class ImexValidateInterceptor extends AbstractValidateInterceptor<Imex> {

	@Override
	public void onValidate(Imex model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);

	}
}
