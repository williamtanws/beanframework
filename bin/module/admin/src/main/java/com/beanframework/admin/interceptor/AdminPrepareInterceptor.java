package com.beanframework.admin.interceptor;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;

public class AdminPrepareInterceptor extends AbstractPrepareInterceptor<Admin> {

	@Override
	public void onPrepare(Admin model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

	}

}
