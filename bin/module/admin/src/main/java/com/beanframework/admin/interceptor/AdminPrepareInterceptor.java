package com.beanframework.admin.interceptor;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;

public class AdminPrepareInterceptor implements PrepareInterceptor<Admin> {

	@Override
	public void onPrepare(Admin model, InterceptorContext context) throws InterceptorException {

	}

}
