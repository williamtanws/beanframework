package com.beanframework.core.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractValidateInterceptor;

public class AuditorValidateInterceptor extends AbstractValidateInterceptor<Auditor> {

	@Override
	public void onValidate(Auditor model, InterceptorContext context) throws InterceptorException {
		super.onValidate(model, context);
	}

}
