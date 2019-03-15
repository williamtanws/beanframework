package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.ValidateInterceptor;

public class AuditorValidateInterceptor implements ValidateInterceptor<Auditor> {

	@Override
	public void onValidate(Auditor model, InterceptorContext context) throws InterceptorException {
	}

}
