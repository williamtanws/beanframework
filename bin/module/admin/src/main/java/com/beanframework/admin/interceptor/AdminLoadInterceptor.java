package com.beanframework.admin.interceptor;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractLoadInterceptor;

public class AdminLoadInterceptor extends AbstractLoadInterceptor<Admin> {

	@Override
	public void onLoad(Admin model, InterceptorContext context) throws InterceptorException {
		super.onLoad(model, context);
	}

}
