package com.beanframework.core.interceptor.admin;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractPrepareInterceptor;
import com.beanframework.user.domain.Admin;

public class AdminPrepareInterceptor extends AbstractPrepareInterceptor<Admin> {

	@Override
	public void onPrepare(Admin model, InterceptorContext context) throws InterceptorException {
		super.onPrepare(model, context);

	}

}
