package com.beanframework.user.interceptor.auditor;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;

public class AuditorLoadInterceptor extends AbstractLoadInterceptor<Auditor> {

	@Override
	public void onLoad(Auditor model, InterceptorContext context) throws InterceptorException {
		Auditor prototype = new Auditor();
		loadCommonProperties(model, prototype, context);
		prototype.setName(model.getName());

		model = prototype;
	}
}
